package com.bigAlsTexture.SpringBucks.controller;

import com.bigAlsTexture.SpringBucks.entity.Coffee;
import com.bigAlsTexture.SpringBucks.service.CoffeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/coffee")
public class CoffeeController {
    @Resource
    private CoffeeService coffeeService;

    @GetMapping("/")
    public List<Coffee> getAll(){
        return coffeeService.findAllCoffee();
    }
}
