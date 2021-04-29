package nameless.common.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 提供缺省bean实现选项的bean factory
 */
@Slf4j
public class AbsentAllowedBeanFactory extends DefaultListableBeanFactory {

    private InvocationHandler invocationHandler = (proxy, method, args) -> null;

    /**
     * 允许无实现的bean列表
     */
    private Set<Class<?>> absentAllowedBeans = new HashSet<>();
    /**
     * 允许无实现的bean所在的package列表
     */
    private Set<String> absentAllowedPackages = new HashSet<>();

    public AbsentAllowedBeanFactory(Set<Class<?>> absentAllowedBeans,
                                    Set<String> absentAllowedBasePackages) {
        this.absentAllowedBeans.addAll(absentAllowedBeans);
        this.absentAllowedPackages.addAll(absentAllowedBasePackages);
    }

    @Override
    protected Map<String, Object> findAutowireCandidates(String beanName,
                                                         Class<?> requiredType,
                                                         DependencyDescriptor descriptor) {
        Map<String, Object> result = super.findAutowireCandidates(beanName, requiredType, descriptor);
        if (result.isEmpty() && requiredType.isInterface() && isAbsentAllowed(requiredType)) {
            log.warn("bean name {} type {} has no implementation in classpath. Create absent bean for it", beanName, requiredType.getName());
            Object absentBean = Proxy.newProxyInstance(requiredType.getClassLoader(), new Class[]{requiredType}, invocationHandler);
            result.put(beanName, absentBean);
        }
        return result;
    }

    private boolean isAbsentAllowed(Class<?> clazz) {
        if (absentAllowedBeans.contains(clazz)) {
            return true;
        }

        Package pkg = clazz.getPackage();
        if (pkg == null) {
            return false;
        }
        String pkgName = pkg.getName();
        while (!absentAllowedPackages.contains(pkgName)) {
            int parentPackageIndex = pkgName.lastIndexOf(".");
            if (parentPackageIndex == -1) {
                return false;
            }
            pkgName = pkgName.substring(0, parentPackageIndex);
        }
        return true;
    }
}
