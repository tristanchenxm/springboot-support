package nameless.common.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import nameless.common.zuul.configuration.EnhancedRoute;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class CustomExtraHeaderFilter extends ZuulFilter {

    private final RouteLocator routeLocator;

    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    public CustomExtraHeaderFilter(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // run before route to downstream (pre decoration)
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        addConfiguredCustomHeaders();
        return null;
    }


    private void addConfiguredCustomHeaders() {
        RequestContext ctx = RequestContext.getCurrentContext();
        final String requestURI = this.urlPathHelper.getPathWithinApplication(ctx.getRequest());
        Route route = this.routeLocator.getMatchingRoute(requestURI);
        if (route instanceof EnhancedRoute) {
            Map<String, String> extraHeaders = ((EnhancedRoute) route).getExtraHeaders();
            if (extraHeaders != null) {
                extraHeaders.forEach(ctx::addZuulRequestHeader);
            }
        }
    }
}

