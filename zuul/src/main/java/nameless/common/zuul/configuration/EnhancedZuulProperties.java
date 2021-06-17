package nameless.common.zuul.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ConfigurationProperties("zuul")
public class EnhancedZuulProperties {

    private Map<String, EnhancedZuulRoute> routes = new LinkedHashMap<>();

    public static class EnhancedZuulRoute extends ZuulProperties.ZuulRoute {
        /**
         * custom extra http headers added to request
         */
        private Map<String, String> extraHeaders;

        public Map<String, String> getExtraHeaders() {
            return extraHeaders;
        }

        public void setExtraHeaders(Map<String, String> extraHeaders) {
            this.extraHeaders = extraHeaders;
        }

    }

    public Map<String, ZuulProperties.ZuulRoute> getRoutes() {
        return new HashMap<>(routes);
    }

    public void setRoutes(Map<String, EnhancedZuulRoute> routes) {
        this.routes = routes;
    }

}
