package com.sami.cache;

import static com.sami.cache.CacheFactory.getCache;

public class CacheUtils {

    public static <K, V> void put(CacheName cacheName, K key, V value) {
        Cache<K, V> cache = getCache(cacheName);
        cache.put(key, value);
    }

    public static <K, V> V get(CacheName cacheName, K key) {
        Cache<K, V> cache = getCache(cacheName);
        return cache.get(key);
    }

    public static <K, V> void remove(CacheName cacheName, K key) {
        Cache<K, V> cache = getCache(cacheName);
        cache.remove(key);
    }

    public static <K, V> void removeAll(CacheName cacheName) {
        Cache<K, V> cache = getCache(cacheName);
        cache.removeAll();
    }
}

