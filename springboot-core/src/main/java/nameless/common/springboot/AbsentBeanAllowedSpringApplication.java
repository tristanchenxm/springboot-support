package nameless.common.springboot;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ResourceLoader;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AbsentBeanAllowedSpringApplication extends SpringApplication {

    private Class<? extends ConfigurableApplicationContext> applicationContextClass;

    public AbsentBeanAllowedSpringApplication(Class<?>... primarySources) {
        super(primarySources);
    }

    public AbsentBeanAllowedSpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
        super(resourceLoader, primarySources);
    }

    /**
     * Static helper that can be used to run a {@link SpringApplication} from the
     * specified source using default settings.
     *
     * @param primarySource the primary source to load
     * @param args          the application arguments (usually passed from a Java main method)
     * @return the running {@link ApplicationContext}
     */
    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        return run(new Class<?>[]{primarySource}, args);
    }

    /**
     * Static helper that can be used to run a {@link SpringApplication} from the
     * specified sources using default settings and user supplied arguments.
     *
     * @param primarySources the primary sources to load
     * @param args           the application arguments (usually passed from a Java main method)
     * @return the running {@link ApplicationContext}
     */
    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        return new AbsentBeanAllowedSpringApplication(primarySources).run(args);
    }

    @Override
    public void setApplicationContextClass(Class<? extends ConfigurableApplicationContext> applicationContextClass) {
        this.applicationContextClass = applicationContextClass;
        super.setApplicationContextClass(applicationContextClass);
    }

    @Override
    protected ConfigurableApplicationContext createApplicationContext() {
        Class<?> contextClass = this.applicationContextClass;
        if (contextClass == null) {
            try {
                switch (getWebApplicationType()) {
                    case SERVLET:
                        contextClass = Class.forName(DEFAULT_SERVLET_WEB_CONTEXT_CLASS);
                        break;
                    case REACTIVE:
                        contextClass = Class.forName(DEFAULT_REACTIVE_WEB_CONTEXT_CLASS);
                        break;
                    default:
                        contextClass = Class.forName(DEFAULT_CONTEXT_CLASS);
                }
            } catch (ClassNotFoundException ex) {
                throw new IllegalStateException(
                    "Unable create a default ApplicationContext, please specify an ApplicationContextClass", ex);
            }
        }
        try {
            Constructor<?> constructor = contextClass.getConstructor(DefaultListableBeanFactory.class);
            Set<Class<?>> beans = new HashSet<>();
            Set<String> basePackages = new HashSet<>();
            findAbsentAllowed(beans, basePackages);
            AbsentAllowedBeanFactory beanFactory = new AbsentAllowedBeanFactory(beans, basePackages);
            return (ConfigurableApplicationContext) BeanUtils.instantiateClass(constructor, beanFactory);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected void findAbsentAllowed(Set<Class<?>> beans, Set<String> basePackages) {
        getAllSources().forEach(source -> {
                if (source instanceof Class<?>) {
                    findAbsentAllowed((Class<?>)source, beans, basePackages);
                }
            }
        );
    }

    private void findAbsentAllowed(Class<?> clazz, Set<Class<?>> beans, Set<String> basePackages) {
        if (clazz == null) {
            return;
        }
        AbsentAllowed absentAllowed = clazz.getAnnotation(AbsentAllowed.class);
        if (absentAllowed != null) {
            beans.addAll(Arrays.asList(absentAllowed.interfaces()));
            basePackages.addAll(Arrays.asList(absentAllowed.basePackages()));
        }
        for (Class<?> inter : clazz.getInterfaces()) {
            findAbsentAllowed(inter, beans, basePackages);
        }
        findAbsentAllowed(clazz.getSuperclass(), beans, basePackages);
    }

}
