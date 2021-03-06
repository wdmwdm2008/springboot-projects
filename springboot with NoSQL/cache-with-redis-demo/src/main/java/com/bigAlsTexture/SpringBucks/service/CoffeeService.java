package com.bigAlsTexture.SpringBucks.service;

import com.bigAlsTexture.SpringBucks.dao.CoffeeDao;
import com.bigAlsTexture.SpringBucks.entity.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
@CacheConfig(cacheNames = "coffee")
public class CoffeeService {

    @Resource
    private CoffeeDao coffeeDao;

    @Cacheable
    public List<Coffee> findAllCoffee() {
        return coffeeDao.findAll();
    }

    @CacheEvict
    public void reloadCoffee(){

    }

    public Optional<Coffee> findOneCoffee(String name){
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("name",  exact().ignoreCase());
        Optional<Coffee> coffee = coffeeDao.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
        log.info("Coffee Found: {}", coffee);
        return coffee;
    }
}
