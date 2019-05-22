package com.bigAlsTexture.SpringBucks;

import com.bigAlsTexture.SpringBucks.entity.Coffee;
import com.bigAlsTexture.SpringBucks.entity.CoffeeOrder;
import com.bigAlsTexture.SpringBucks.entity.OrderState;
import com.bigAlsTexture.SpringBucks.service.CoffeeOrderService;
import com.bigAlsTexture.SpringBucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import javax.annotation.Resource;
import java.util.Arrays;

@Slf4j
public class SpringBucksRunner implements ApplicationRunner {

    @Resource
    private CoffeeService coffeeService;

    @Resource
    private CoffeeOrderService coffeeOrderService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        coffeeService.initCache()
                .then(coffeeService.findOneCoffee("mocha")
                    .flatMap(c->{
                        CoffeeOrder order = createOrder("Li Lei", c);
                        return coffeeOrderService.create(order);
                    })
                    .doOnError(t->log.error("error {}", t)))
                .subscribe(o -> log.info("Create Order:{}", o));
        log.info("After Subscribe");
        Thread.sleep(5000);
    }

    private CoffeeOrder createOrder(String customer, Coffee... coffee) {
        return CoffeeOrder.builder()
                .customer(customer)
                .items(Arrays.asList(coffee))
                .state(OrderState.INIT)
                .build();
    }
}
