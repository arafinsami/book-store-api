package com.sami.cache;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheFactory implements ApplicationContextAware {

	private static HazelcastInstance hazelcastInstance;

	private static Map<CacheName, Cache> cacheHolder = new ConcurrentHashMap<>();

	@SuppressWarnings({ "unchecked" })
	public static <K, V> Cache<K, V> getCache(CacheName cacheName) {
		Cache<K, V> cache = cacheHolder.get(cacheName);

		if (cache == null) {
			cache = new Cache<>(hazelcastInstance.getMap(cacheName.getName()));
			cacheHolder.putIfAbsent(cacheName, cache);

			return cacheHolder.get(cacheName);
		}

		return cache;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		hazelcastInstance = applicationContext.getBean(CacheConstants.HAZELCAST_INSTANCE_NAME, HazelcastInstance.class);
	}
}