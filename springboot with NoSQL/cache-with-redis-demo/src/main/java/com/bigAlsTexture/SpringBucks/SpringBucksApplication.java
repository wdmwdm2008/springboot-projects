package com.bigAlsTexture.SpringBucks;

import com.bigAlsTexture.SpringBucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@EnableCaching(proxyTargetClass = true)
@Slf4j
public class SpringBucksApplication implements ApplicationRunner {

	@Resource
	CoffeeService coffeeService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("Count: {}", coffeeService.findAllCoffee().size());
		for(int i = 0; i < 10; i++){
			log.info("Reading All Coffees!");
			coffeeService.findAllCoffee();
		}

		Thread.sleep(5000);
		log.info("Reading after refresh!");
		coffeeService.findAllCoffee().forEach(c->log.info("Coffee: {}", c.getName()));
	}
}
