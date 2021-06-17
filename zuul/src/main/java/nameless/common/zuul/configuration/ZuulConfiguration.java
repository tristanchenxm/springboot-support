package nameless.common.zuul.configuration;

import com.netflix.zuul.monitoring.CounterFactory;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.netflix.zuul.ZuulProxyAutoConfiguration;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
import org.springframework.cloud.netflix.zuul.metrics.DefaultCounterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({EnhancedZuulProperties.class})
public class ZuulConfiguration extends ZuulProxyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(EnhancedDiscoveryClientRouteLocator.class)
    public EnhancedDiscoveryClientRouteLocator discoveryRouteLocator(
            @Autowired(required = false) Registration registration,
            DiscoveryClient discovery,
            ServiceRouteMapper serviceRouteMapper,
            EnhancedZuulProperties enhancedZuulProperties) {
        return new EnhancedDiscoveryClientRouteLocator(this.server.getServlet().getContextPath(),
                discovery, this.zuulProperties, serviceRouteMapper, registration, enhancedZuulProperties);
    }

    @Bean
    public CounterFactory counterFactory(MeterRegistry meterRegistry) {
        return new DefaultCounterFactory(meterRegistry);
    }

}
