package com.bigAlsTexture.SpringBucks.controller;

import com.bigAlsTexture.SpringBucks.controller.request.NewOrderRequest;
import com.bigAlsTexture.SpringBucks.entity.Coffee;
import com.bigAlsTexture.SpringBucks.entity.CoffeeOrder;
import com.bigAlsTexture.SpringBucks.service.CoffeeOrderService;
import com.bigAlsTexture.SpringBucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {

    @Resource
    private CoffeeOrderService coffeeOrderService;

    @Resource
    private CoffeeService coffeeService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrderRequest){
        log.info("Receive new Order {}", newOrderRequest);
        Coffee[] coffeeList = coffeeService.findCoffeeByName(newOrderRequest.getItems()).toArray(new Coffee[]{});
        return coffeeOrderService.createOrder(newOrderRequest.getCustomer(), coffeeList);
    }
}
