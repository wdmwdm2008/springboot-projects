package com.bigAlsTexture.SpringBucks.service;

import com.bigAlsTexture.SpringBucks.dao.CoffeeDao;
import com.bigAlsTexture.SpringBucks.entity.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
@Transactional
@CacheConfig(cacheNames = "CoffeeCache")
public class CoffeeService {

    @Resource
    private CoffeeDao coffeeDao;

    @Cacheable
    public List<Coffee> findAllCoffee() {
        return coffeeDao.findAll();
    }

    public List<Coffee> findCoffeeByName(List<String> name){
        return coffeeDao.findByNameInOrderById(name);
    }
}
