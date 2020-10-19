package com.sami.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;

@ComponentScan(basePackages = "com.sami.cache")
@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	CacheManager cacheManager() {
		return new HazelcastCacheManager(hazelcastInstance());
	}

	@Bean
	public Config config() {
		Config config = new ClasspathXmlConfig(CacheConstants.HAZELCAST_CONFIG_NAME);
		config.setInstanceName(CacheConstants.HAZELCAST_INSTANCE_NAME);

		return config;
	}

	@Bean
	public HazelcastInstance hazelcastInstance() {
		return Hazelcast.newHazelcastInstance(config());
	}
}
