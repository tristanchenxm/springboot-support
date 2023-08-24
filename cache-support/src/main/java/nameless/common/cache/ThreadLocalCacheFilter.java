package nameless.common.cache;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * servlet filter that enables cache for the current thread.
 */
public class ThreadLocalCacheFilter extends OncePerRequestFilter {

    private final ThreadLocalCacheManager threadLocalCacheManager;

    public ThreadLocalCacheFilter(ThreadLocalCacheManager threadLocalCacheManager) {
        this.threadLocalCacheManager = threadLocalCacheManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        threadLocalCacheManager.enableCache();
        filterChain.doFilter(request, response);
        threadLocalCacheManager.clearCache();
    }
}
