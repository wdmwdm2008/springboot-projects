package geektime.spring.web.context;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestBean1 {
    private String context;
    public void hello(){
        System.out.println("Hello " + context);
    }
}
