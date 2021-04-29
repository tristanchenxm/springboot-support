package nameless.common.springboot.test.context;

import org.springframework.test.context.ContextLoader;

/**
 * sample
 * <pre>
 *     \@BootstrapWith(SpringBootTestContextBootstrapper.class)
 *     public class FooTest {
 *          \@Test
 *          public void test(){
 *              // ...
 *          }
 *     }
 * </pre>
 */
public class SpringBootTestContextBootstrapper extends org.springframework.boot.test.context.SpringBootTestContextBootstrapper {
    @Override
    protected Class<? extends ContextLoader> getDefaultContextLoaderClass(Class<?> testClass) {
        return SpringBootContextLoader.class;
    }

}
