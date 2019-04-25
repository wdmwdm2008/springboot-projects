package com.bigAlsTexture.SpringBucks.service;

import com.bigAlsTexture.SpringBucks.dao.CoffeeCacheDao;
import com.bigAlsTexture.SpringBucks.dao.CoffeeDao;
import com.bigAlsTexture.SpringBucks.entity.Coffee;
import com.bigAlsTexture.SpringBucks.entity.CoffeeCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
public class CoffeeService {

    @Resource
    private CoffeeDao coffeeDao;

    @Resource
    private CoffeeCacheDao coffeeCacheDao;

    public List<Coffee> findAllCoffee() {
        return coffeeDao.findAll();
    }

    public Optional<Coffee> findOneByName(String name){
        Optional<CoffeeCache> cached = coffeeCacheDao.findOneByName(name);
        if(cached.isPresent()){
            CoffeeCache coffeeCache = cached.get();
            Coffee coffee = Coffee.builder()
                                    .name(coffeeCache.getName())
                                    .price(coffeeCache.getMoney())
                                    .build();
            log.info("Coffee {} found in cache.", coffeeCache);
            return Optional.of(coffee);
        } else {
            Optional<Coffee> raw = findOneCoffee(name);
            raw.ifPresent(c->{
                CoffeeCache coffeeCache = CoffeeCache.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .money(c.getPrice())
                        .build();
                log.info("Save Coffee {} to cache.", coffeeCache);
                coffeeCacheDao.save(coffeeCache);
            });
            return raw;
        }
    }

    public Optional<Coffee> findOneCoffee(String name){
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("name",  exact().ignoreCase());
        Optional<Coffee> coffee = coffeeDao.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
        log.info("Coffee Found: {}", coffee);
        return coffee;
    }
}
