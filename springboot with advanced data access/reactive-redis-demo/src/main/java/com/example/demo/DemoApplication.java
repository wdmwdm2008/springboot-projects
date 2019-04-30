package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@Slf4j
public class DemoApplication implements ApplicationRunner {

	private static final String key = "COFFEE_MENU";

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ReactiveStringRedisTemplate redisTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	ReactiveStringRedisTemplate reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
		return new ReactiveStringRedisTemplate(factory);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		ReactiveHashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
		CountDownLatch cdl = new CountDownLatch(1);

		List<Coffee> list = jdbcTemplate.query("Select * from t_coffee", (rs, i) ->
			Coffee.builder()
					.id(rs.getLong("id"))
					.name(rs.getString("name"))
					.price(rs.getLong("price"))
					.build()
		);

		Flux.fromIterable(list)
				.publishOn(Schedulers.single())
				.doOnComplete(()->log.info("list okey!"))
				.flatMap(c->{
					log.info("Try to put {}, {}", c.getName(), c.getPrice());
					return hashOps.put(key,c.getName(), c.getPrice().toString());
				})
				.doOnComplete(()->log.info("set ok"))
				.concatWith(redisTemplate.expire(key, Duration.ofMinutes(1)))
				.doOnComplete(()->log.info("expire ok"))
				.onErrorResume(e->{
					log.error("expection {}", e.getMessage());
					return Mono.just(false);
				})
				.subscribe(b->log.info("Boolean:{}", b),
						e -> log.info("Exception: [}", e.getMessage()),
						() -> {
							log.info("Count down section!");
							cdl.countDown();
						}
				);
		log.info("waiting!");
		cdl.await();
	}
}
