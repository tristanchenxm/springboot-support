package nameless.common.zuul.configuration;

import nameless.common.zuul.configuration.EnhancedZuulProperties.EnhancedZuulRoute;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;

public class EnhancedDiscoveryClientRouteLocator extends DiscoveryClientRouteLocator {

    public EnhancedDiscoveryClientRouteLocator(String servletPath, DiscoveryClient discovery,
                                               ZuulProperties properties, ServiceInstance localServiceInstance,
                                               EnhancedZuulProperties enhancedZuulProperties) {
        super(servletPath, discovery, properties, localServiceInstance);
        replaceRoutes(properties, enhancedZuulProperties);
    }

    public EnhancedDiscoveryClientRouteLocator(String servletPath, DiscoveryClient discovery,
                                               ZuulProperties properties, ServiceRouteMapper serviceRouteMapper,
                                               ServiceInstance localServiceInstance,
                                               EnhancedZuulProperties enhancedZuulProperties) {
        super(servletPath, discovery, properties, serviceRouteMapper, localServiceInstance);
        replaceRoutes(properties, enhancedZuulProperties);
    }

    private void replaceRoutes(ZuulProperties properties, EnhancedZuulProperties enhancedZuulProperties) {
        properties.setRoutes(enhancedZuulProperties.getRoutes());
        properties.init();
    }

    @Override
    protected Route getRoute(ZuulProperties.ZuulRoute route, String path) {
        return new EnhancedRoute(super.getRoute(route, path), ((EnhancedZuulRoute) route).getExtraHeaders());
    }
}
