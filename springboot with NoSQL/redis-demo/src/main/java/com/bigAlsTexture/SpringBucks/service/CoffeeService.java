package com.bigAlsTexture.SpringBucks.service;

import com.bigAlsTexture.SpringBucks.dao.CoffeeDao;
import com.bigAlsTexture.SpringBucks.entity.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
public class CoffeeService {

    @Resource
    private RedisTemplate<String, Coffee> redisTemplate;

    private static final String CACHE = "springbucks-coffee";

    @Resource
    private CoffeeDao coffeeDao;

    public List<Coffee> findAllCoffee() {
        return coffeeDao.findAll();
    }

    public Optional<Coffee> findOneCoffee(String name){
        HashOperations<String, String, Coffee> hashOperations = redisTemplate.opsForHash();
        if(redisTemplate.hasKey(CACHE) && hashOperations.hasKey(CACHE, name)){
            log.info("Get coffee {} from redis.", name);
            return Optional.of(hashOperations.get(CACHE, name));
        }

        ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("name",  exact().ignoreCase());
        Optional<Coffee> coffee = coffeeDao.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
        log.info("Coffee Found: {}", coffee);
        if(coffee.isPresent()){
            log.info("put coffee {} to redis", name);
            hashOperations.put(CACHE, name, coffee.get());
            redisTemplate.expire(CACHE, 1, TimeUnit.MINUTES);
        }
        return coffee;
    }
}
