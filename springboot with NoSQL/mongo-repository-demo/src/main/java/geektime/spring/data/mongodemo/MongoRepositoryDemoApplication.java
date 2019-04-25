package geektime.spring.data.mongodemo;

import geektime.spring.data.mongodemo.converter.MoneyReadConverter;
import geektime.spring.data.mongodemo.dao.CoffeeDao;
import geektime.spring.data.mongodemo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@SpringBootApplication
@EnableMongoRepositories
public class MongoRepositoryDemoApplication implements CommandLineRunner {

	@Resource
	CoffeeDao coffeeDao;


	public static void main(String[] args) {
		SpringApplication.run(MongoRepositoryDemoApplication.class, args);
	}

	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
	}

	@Override
	public void run(String... args) throws Exception {
		Coffee espresso = Coffee.builder()
				.name("espresso")
				.price(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.createTime(new Date())
				.updateTime(new Date()).build();
		Coffee latte = Coffee.builder()
				.name("latte")
				.price(Money.of(CurrencyUnit.of("CNY"), 30.0))
				.createTime(new Date())
				.updateTime(new Date()).build();

		coffeeDao.insert(Arrays.asList(espresso, latte));
		coffeeDao.findAll(Sort.by("name")).forEach(e -> log.info("Saved Coffee: {}", e));

		Thread.sleep(1000);
		latte.setPrice(Money.of(CurrencyUnit.of("CNY"), 25));
		latte.setUpdateTime(new Date());
		coffeeDao.save(latte);
		coffeeDao.findByName("latte").forEach(e -> log.info("Coffee: {}", e));
		coffeeDao.deleteAll();
	}
}

