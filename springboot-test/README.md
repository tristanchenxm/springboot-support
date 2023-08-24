## ThreadLocal level cache
### how-to-use in springboot projects
```java
@Configuration
@EnableCache
public class CacheConfiguration {
    /**
     * enable cache by set servlet filter
     */
    @Bean
    public ThreadLocalCacheFilter threadLocalCacheFilter(ThreadLocalCacheManager cacheManager) {
        return new ThreadLocalCacheFilter(cacheManager);
    }
    
    /**
     * set cache manager
     */
    @Bean
    public ThreadLocalCacheManager threadLocalCacheManager() {
        return new ThreadLocalCacheManager();
    }
}
```