package geektime.spring.web.foo;

import geektime.spring.web.context.TestBean1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class FooConfig1 {

    @Bean
    public TestBean1 testBeanX1(){
        return new TestBean1("foo1");
    }

    @Bean
    public TestBean1 testBeanY1(){
        return new TestBean1("foo1");
    }

    @Bean
    public FooAspect1 fooAspect() {
        return new FooAspect1();
    }
}
