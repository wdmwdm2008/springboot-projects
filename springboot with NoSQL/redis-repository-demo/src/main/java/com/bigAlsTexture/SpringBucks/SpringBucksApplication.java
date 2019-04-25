package com.bigAlsTexture.SpringBucks;

import com.bigAlsTexture.SpringBucks.converter.BytesToMoneyConverter;
import com.bigAlsTexture.SpringBucks.converter.MoneyToBytesConverter;
import com.bigAlsTexture.SpringBucks.entity.Coffee;
import com.bigAlsTexture.SpringBucks.entity.CoffeeCache;
import com.bigAlsTexture.SpringBucks.service.CoffeeService;
import io.lettuce.core.ReadFrom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Optional;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@Slf4j
@EnableRedisRepositories
public class SpringBucksApplication implements ApplicationRunner {

    @Resource
    CoffeeService coffeeService;
	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}

    @Bean
    public LettuceClientConfigurationBuilderCustomizer customizer() {
        return builder -> builder.readFrom(ReadFrom.MASTER_PREFERRED);
    }

    @Bean
	public RedisCustomConversions redisCustomConversions(){
	    return new RedisCustomConversions(
                Arrays.asList(new MoneyToBytesConverter(), new BytesToMoneyConverter())
        );
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Coffee> c = coffeeService.findOneByName("mocha");
        log.info("Coffee: {}", c);
        Thread.sleep(61000);
        for(int i= 0; i < 5; i++){
            c = coffeeService.findOneByName("mocha");
        }
        log.info("Value from Redis: {}", c);
    }
}
