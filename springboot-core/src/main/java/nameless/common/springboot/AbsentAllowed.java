package nameless.common.springboot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注允许不实现的bean
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AbsentAllowed {
    /**
     * 允许无实现的interface列表
     */
    Class<?>[] interfaces() default {};

    /**
     * 允许无实现的base package
     */
    String[] basePackages() default {};
}
