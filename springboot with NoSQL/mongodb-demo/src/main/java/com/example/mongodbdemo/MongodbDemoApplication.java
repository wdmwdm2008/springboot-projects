package com.example.mongodbdemo;

import com.example.mongodbdemo.entity.Coffee;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@SpringBootApplication
@Slf4j
public class MongodbDemoApplication implements ApplicationRunner {

	@Resource
	MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(MongodbDemoApplication.class, args);
	}

	@Bean
	public MongoCustomConversions mongoCustomConversions(){
		return new MongoCustomConversions(Arrays.asList(new MongoReadConversion()));
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Coffee coffee = Coffee.builder()
					.name("espresso")
					.price(Money.of(CurrencyUnit.of("CNY"), 20.00))
					.create_time(new Date())
					.update_time(new Date())
					.build();

		Coffee saved = mongoTemplate.save(coffee);
		log.info("Coffee: {}", saved);


		List<Coffee> list =  mongoTemplate.find(Query.query(where("name").is("espresso")), Coffee.class);
		log.info("Find {} Coffee: ", list.size());
		list.forEach(c -> log.info("Coffee: {}", c));

		Thread.sleep(1000);
		UpdateResult result = mongoTemplate.updateFirst(query(where("name").is("espresso")),
				new Update().set("price", Money.ofMajor(CurrencyUnit.of("CNY"), 30)).currentDate("update_time"),
				Coffee.class);
		log.info("Update Result: {}", result.getModifiedCount());

		Coffee updateOne = mongoTemplate.findById(saved.getId(), Coffee.class);
		log.info("Update Result: {}", updateOne);
		mongoTemplate.remove(updateOne);
	}
}
