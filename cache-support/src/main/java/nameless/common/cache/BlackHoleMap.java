package nameless.common.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class BlackHoleMap<K, V> implements Map<K, V> {
    private static final BlackHoleMap INSTANCE = new BlackHoleMap();
    private BlackHoleMap() {
    }

    @SuppressWarnings("unchecked")
    public static <K, V> BlackHoleMap<K, V> getInstance() {
        return (BlackHoleMap<K, V>) INSTANCE;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
    }

    @Override
    public void clear() {
    }

    @Override
    public Set<K> keySet() {
        return Collections.emptySet();
    }

    @Override
    public Collection<V> values() {
        return Collections.emptyList();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return Collections.emptySet();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return null;
    }
}
