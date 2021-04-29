package nameless.common.springboot.test.context;

import nameless.common.springboot.AbsentBeanAllowedSpringApplication;
import org.springframework.boot.SpringApplication;

public class SpringBootContextLoader extends org.springframework.boot.test.context.SpringBootContextLoader {
    @Override
    protected SpringApplication getSpringApplication() {
        return new AbsentBeanAllowedSpringApplication();
    }
}
