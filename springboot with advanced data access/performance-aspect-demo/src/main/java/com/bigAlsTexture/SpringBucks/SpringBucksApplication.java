package com.bigAlsTexture.SpringBucks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@EnableAspectJAutoProxy
public class SpringBucksApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}
}
