package nameless.common.zuul.configuration;

import org.springframework.cloud.netflix.zuul.filters.Route;

import java.util.HashMap;
import java.util.Map;

public class EnhancedRoute extends Route {
    /**
     * custom extra http headers added to request
     */
    private Map<String, String> extraHeaders = new HashMap<>();

    public EnhancedRoute(Route route, Map<String, String> extraHeaders) {
        super(route.getId(), route.getPath(), route.getLocation(), route.getPrefix(), route.getRetryable(), route.getSensitiveHeaders());
        if (extraHeaders != null) {
            this.extraHeaders.putAll(extraHeaders);
        }
    }

    public Map<String, String> getExtraHeaders() {
        return extraHeaders;
    }

    public void setExtraHeaders(Map<String, String> extraHeaders) {
        this.extraHeaders = extraHeaders;
    }

}
