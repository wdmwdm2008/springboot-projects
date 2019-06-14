package geektime.spring.web.context;

import geektime.spring.web.foo.FooConfig;
import geektime.spring.web.foo.FooConfig1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
@Slf4j
public class ContextHierarchyDemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ContextHierarchyDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		ApplicationContext applicationContext1 = new AnnotationConfigApplicationContext(FooConfig1.class);
		ApplicationContext applicationContext2 = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"}, applicationContext1);
		TestBean1 bean = applicationContext1.getBean("testBeanX1", TestBean1.class);
		bean.hello();

		log.info("=============");
		bean = applicationContext2.getBean("testBeanX1", TestBean1.class);
		bean.hello();


		bean = applicationContext2.getBean("testBeanY1", TestBean1.class);
		bean.hello();
	}
}
