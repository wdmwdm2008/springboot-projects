package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.scheduler.Schedulers;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static sun.misc.MessageUtils.where;

@SpringBootApplication
@Slf4j
public class DemoApplication implements ApplicationRunner {

	private static final String key = "COFFEE_MENU";

	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;
	private CountDownLatch cdl = new CountDownLatch(2);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		startFromInsertion(()->{
			log.info("Runnable");
			descreaseHighPrice();
		});
		log.info("after starting!");
//		descreaseHighPrice();
		cdl.await();
	}

	private void startFromInsertion(Runnable runnable){
		reactiveMongoTemplate.insertAll(initCoffee())
				.publishOn(Schedulers.elastic())
				.doOnNext(c -> log.info("Next: {}", c))
				.doOnComplete(runnable)
				.doFinally(s -> {
					cdl.countDown();
					log.info("Finally 1, {}", s);
				})
				.count()
				.subscribe(c->log.info("Insert {} records", c));
	}

	private void descreaseHighPrice(){
		reactiveMongoTemplate.updateMulti(
				Query.query(Criteria.where("price").gte(30L)),
				new Update().inc("price",-5L),
				Coffee.class
		).doFinally(c->{
			cdl.countDown();
			log.info("Finally 2, {}", c);
		})
		.subscribe(r->log.info("Result is {}", r));
	}

	private List<Coffee> initCoffee(){
		Coffee espresso = Coffee.builder()
				.name("espresso")
				.price(20L)
				.build();
		Coffee latte = Coffee.builder()
				.name("latte")
				.price(30L)
				.build();
		return Arrays.asList(espresso, latte);
	}
}
