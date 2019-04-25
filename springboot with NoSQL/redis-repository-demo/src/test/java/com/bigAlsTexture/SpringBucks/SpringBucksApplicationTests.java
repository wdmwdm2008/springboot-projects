package com.bigAlsTexture.SpringBucks;

import com.bigAlsTexture.SpringBucks.dao.CoffeeDao;
import com.bigAlsTexture.SpringBucks.entity.Coffee;
import com.bigAlsTexture.SpringBucks.entity.CoffeeOrder;
import com.bigAlsTexture.SpringBucks.entity.OrderState;
import com.bigAlsTexture.SpringBucks.service.CoffeeOrderService;
import com.bigAlsTexture.SpringBucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringBucksApplicationTests {

	@Resource
	private CoffeeDao coffeeDao;

	@Resource
	private CoffeeOrderService coffeeOrderService;

	@Resource
	private CoffeeService coffeeService;

	@Test
	public void contextLoads() {
		log.info("All Coffee: {}", coffeeDao.findAll());

		Optional<Coffee> latte = coffeeService.findOneCoffee("Latte");
		if(latte.isPresent()){
			CoffeeOrder order = coffeeOrderService.createOrder("Li Lei", latte.get());
			log.info("Update INIT to PAID: {}", coffeeOrderService.updateState(order, OrderState.PAID));
			log.info("Update PAID to INIT: {}", coffeeOrderService.updateState(order, OrderState.INIT));
		}
	}
}
