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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((configs == null) ? 0 : configs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppVersionResponse other = (AppVersionResponse) obj;
		if (configs == null) {
			if (other.configs != null)
				return false;
		} else if (!configs.equals(other.configs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AppVersionResponse [configs=" + configs + "]";
	}
	
}
