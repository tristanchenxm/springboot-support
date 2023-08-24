package nameless.common.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;

public class CacheTest {

    @Test
    public void test1() {
        String cacheName1 = "c-1";
        String cacheKey1 = "k-1";
        ThreadLocalCacheManager manager = new ThreadLocalCacheManager();
        Assertions.assertTrue(manager.getCacheNames().isEmpty());

        Cache cache1 = manager.getCache(cacheName1);
        Assertions.assertNotNull(cache1);
        Assertions.assertNull(cache1.get(cacheKey1));
        cache1.put(cacheKey1, "v-1");
        Assertions.assertNull(cache1.get(cacheKey1));

        manager.enableCache();
        cache1 = manager.getCache(cacheName1);
        Assertions.assertNull(cache1.get(cacheKey1));
        cache1.put(cacheKey1, "v-1");
        Assertions.assertNotNull(cache1.get(cacheKey1));

        manager.clearCache();
        manager.enableCache();
        cache1 = manager.getCache(cacheName1);
        Assertions.assertNull(cache1.get(cacheKey1));
        cache1.put(cacheKey1, "v-1");
        Assertions.assertNotNull(cache1.get(cacheKey1));
        
        Assertions.assertEquals(1, manager.getCacheNames().size());
        Assertions.assertTrue(manager.getCacheNames().contains(cacheName1));
    }
}
