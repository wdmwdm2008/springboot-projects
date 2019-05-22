package com.bigAlsTexture.SpringBucks.service;


import com.bigAlsTexture.SpringBucks.dao.CoffeeOrderDao;
import com.bigAlsTexture.SpringBucks.entity.Coffee;
import com.bigAlsTexture.SpringBucks.entity.CoffeeOrder;
import com.bigAlsTexture.SpringBucks.entity.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@Slf4j
public class CoffeeOrderService {

    @Resource
    private CoffeeOrderDao coffeeOrderDao;


    public Mono<Long> create(CoffeeOrder order){
        return coffeeOrderDao.save(order);
    }
}
