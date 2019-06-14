package geektime.spring.web.foo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Slf4j
public class FooAspect1 {

    @AfterReturning("bean(testBeanX1*)")
    public void printAfter(){
        log.info("after Hello1-1()");
    }
}
