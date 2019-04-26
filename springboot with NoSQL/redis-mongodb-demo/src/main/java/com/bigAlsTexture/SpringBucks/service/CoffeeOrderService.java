package com.bigAlsTexture.SpringBucks.service;


import com.bigAlsTexture.SpringBucks.dao.CoffeeOrderDao;
import com.bigAlsTexture.SpringBucks.entity.Coffee;
import com.bigAlsTexture.SpringBucks.entity.CoffeeOrder;
import com.bigAlsTexture.SpringBucks.entity.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Service
@Slf4j
public class CoffeeOrderService {

    @Resource
    private CoffeeOrderDao coffeeOrderDao;

    public CoffeeOrder createOrder(String customer, Coffee coffee){
        CoffeeOrder order = CoffeeOrder.builder()
                                .customer(customer)
                                .items(new ArrayList<>(Arrays.asList(coffee)))
                                .create_time(new Date())
                                .update_time(new Date())
                                .state(OrderState.INIT)
                                .build();
        CoffeeOrder saved = coffeeOrderDao.save(order);
        log.info("New Order: {}", saved);
        return saved;
    }


    public boolean updateState(CoffeeOrder order, OrderState state){
        if(state.compareTo(order.getState()) <= 0){
            log.warn("Wrong state order: {}, {}", state, order.getState());
            return false;
        }
        order.setState(state);
        coffeeOrderDao.save(order);
        log.info("Updated Order: {}", order);
        return true;
    }
}
