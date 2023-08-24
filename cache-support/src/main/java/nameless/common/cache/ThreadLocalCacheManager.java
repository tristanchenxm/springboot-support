package nameless.common.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * CacheManager that stores caches only available in the current thread.
 */
public class ThreadLocalCacheManager implements CacheManager {
    private final ThreadLocal<Map<String, Cache>> cacheMapLocal = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<Boolean> cacheEnabledLocal = ThreadLocal.withInitial(() -> Boolean.FALSE);

    @Override
    public Cache getCache(String name) {
        Map<String, Cache> cacheMap = cacheMapLocal.get();
        Cache cache = cacheMap.get(name);
        if (cache == null) {
            cache = new MapCache(name, newCacheMap());
            cacheMap.put(name, cache);
        }
        return cache;
    }

    private <K, V> Map<K, V> newCacheMap() {
        return isCacheEnabled() ? new HashMap<>() : BlackHoleMap.getInstance();
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheMapLocal.get().keySet();
    }

    public void enableCache() {
        cacheMapLocal.get().clear();
        cacheEnabledLocal.set(true);
    }

    private boolean isCacheEnabled() {
        return cacheEnabledLocal.get();
    }
    public void clearCache() {
        cacheMapLocal.get().clear();
        cacheEnabledLocal.set(false);
    }
}
