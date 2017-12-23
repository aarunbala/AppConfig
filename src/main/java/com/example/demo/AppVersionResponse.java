package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class AppVersionResponse {
	Map<String, Object> configs;

	public Map<String, Object> getConfigs() {
		if(configs==null) {
			this.configs = new HashMap<>();
		}
		return configs;
	}

	public void setConfigs(Map<String, Object> configs) {
		this.configs = configs;
	}
	
	public void put(String key, Object value) {
		if(configs==null) {
			this.configs = new HashMap<>();
		}
		this.configs.put(key, value);
	}

	@Override
	public String toString() {
		return "AppVersionResponse [configs=" + configs + "]";
	}
	
}
