package com.sami.cache;

import java.util.Map;

public class Cache<K, V> {

	private Map<K, V> keyValueMap;

	public Cache(Map<K, V> keyValueMap) {
		this.keyValueMap = keyValueMap;
	}

	public void put(K key, V value) {
		this.keyValueMap.put(key, value);
	}

	public V get(K key) {
		return this.keyValueMap.get(key);
	}

	public void remove(K key) {
		this.keyValueMap.remove(key);
	}

	public void removeAll() {
		this.keyValueMap.clear();
	}
}

