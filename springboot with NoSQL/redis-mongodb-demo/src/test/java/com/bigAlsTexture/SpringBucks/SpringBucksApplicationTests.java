package com.bigAlsTexture.SpringBucks;

import com.bigAlsTexture.SpringBucks.dao.CoffeeDao;
import com.bigAlsTexture.SpringBucks.dao.CoffeeOrderDao;
import com.bigAlsTexture.SpringBucks.entity.Coffee;
import com.bigAlsTexture.SpringBucks.entity.CoffeeOrder;
import com.bigAlsTexture.SpringBucks.entity.OrderState;
import com.bigAlsTexture.SpringBucks.service.CoffeeOrderService;
import com.bigAlsTexture.SpringBucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringBucksApplicationTests {

	@Resource
	private CoffeeDao coffeeDao;

    @Resource
    private CoffeeOrderDao coffeeOrderDao;

	@Resource
	private CoffeeOrderService coffeeOrderService;

	@Resource
	private CoffeeService coffeeService;

	private void initialData(){
        Map<String, Money> initMap = new HashMap<>();
        initMap.put("espresso", Money.ofMinor(CurrencyUnit.of("CAD"), 500));
        initMap.put("latte", Money.ofMinor(CurrencyUnit.of("CAD"), 450));
        initMap.put("capuccino", Money.ofMinor(CurrencyUnit.of("CAD"), 400));
        initMap.put("mocha", Money.ofMinor(CurrencyUnit.of("CAD"), 350));
        initMap.put("macchiato", Money.ofMinor(CurrencyUnit.of("CAD"), 300));

        initMap.forEach((key, value)->{
            coffeeDao.insert(Coffee
                    .builder()
                    .name(key)
                    .price(value)
                    .create_time(new Date())
                    .update_time(new Date())
                    .build());
        });
    }

	@Test
	public void contextLoads() throws InterruptedException {
        coffeeDao.deleteAll();
        coffeeOrderDao.deleteAll();
	    initialData();
		log.info("All Coffee: {}", coffeeDao.findAll());
		for(int i =0 ; i < 10; i++){
			log.info("Reading coffee!");
			coffeeDao.findAll();
		}
		Thread.sleep(5000);
		log.info("Reading Coffee After fresh!");
		coffeeService.findAllCoffee().forEach(c->log.info("Coffee: {}", c.getName()));
		Optional<Coffee> latte = coffeeService.findOneCoffee("Latte");
		if(latte.isPresent()){
			CoffeeOrder order = coffeeOrderService.createOrder("Li Lei", latte.get());
			log.info("Update INIT to PAID: {}", coffeeOrderService.updateState(order, OrderState.PAID));
			log.info("Update PAID to INIT: {}", coffeeOrderService.updateState(order, OrderState.INIT));
		}
	}
}
