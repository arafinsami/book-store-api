package com.sami.cache;


public enum CacheName {

	AUTH_DATA("auth-data"), COMMON_DATA("common-data");

	private String name;

	CacheName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}