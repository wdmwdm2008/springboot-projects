package com.bigAlsTexture.SpringBucks;

import com.bigAlsTexture.SpringBucks.converter.MongoReadConverter;
import com.bigAlsTexture.SpringBucks.converter.RedisReadConverter;
import com.bigAlsTexture.SpringBucks.converter.RedisWriteConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.*;

@SpringBootApplication
@EnableTransactionManagement
@EnableMongoRepositories
@EnableCaching(proxyTargetClass = true)
@Slf4j
public class SpringBucksApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}

	@Bean
	public MongoCustomConversions mongoCustomConversions(){
		return new MongoCustomConversions(Arrays.asList(new MongoReadConverter()));
	}

	@Bean
	public RedisCustomConversions redisCustomConversions(){
		return new RedisCustomConversions(Arrays.asList(new RedisWriteConverter(), new RedisReadConverter()));
	}
}
