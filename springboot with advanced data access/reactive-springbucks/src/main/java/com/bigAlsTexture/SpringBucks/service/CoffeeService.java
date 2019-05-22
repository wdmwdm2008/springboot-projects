package com.bigAlsTexture.SpringBucks.service;

import com.bigAlsTexture.SpringBucks.dao.CoffeeDao;
import com.bigAlsTexture.SpringBucks.entity.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
public class CoffeeService {

    private static final String PREFIX = "springbucks-";

    @Resource
    private CoffeeDao coffeeDao;

    @Resource
    private ReactiveRedisTemplate<String, Coffee> reactiveRedisTemplate;

    public Flux<Boolean> initCache(){
        return coffeeDao.findAll()
                .flatMap(c->reactiveRedisTemplate.opsForValue()
                    .set(PREFIX + c.getName(), c)
                    .flatMap(b -> reactiveRedisTemplate.expire(PREFIX + c.getName(), Duration.ofMinutes(1)))
                    .doOnSuccess(v -> log.info("loading and caching {}", c)));
    }

    public Mono<Coffee> findOneCoffee(String name){
        return reactiveRedisTemplate.opsForValue().get("PREFIX" + name)
                .switchIfEmpty(coffeeDao.findByName(name)
                    .doOnSuccess(s->log.info("Loading {} from DB", s)));

    }
}
