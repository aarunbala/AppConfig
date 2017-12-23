package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.model.AppVersionConfig;
import com.example.demo.model.AppVersionKey;

public class TestMain {

	public static void main(String[] args) {
		String appName = "App";
		String appVersion = "1.2";
		String platformName = "ios";
		String platformVersion = "11.2";
		AppVersionResponse response = new AppVersionResponse();
		AppVersionKey key = getAppVersionKey(appName, appVersion, platformName, platformVersion);
		AppVersionKey global = getGlobalAppVersionKey(appName);
		
		Map<String, Object> keyMap = filterConfig(key.getConfigs());
		Map<String,Object> globalKeyMap = filterGlobalConfig(keyMap, global.getConfigs());
		
		keyMap.putAll(globalKeyMap);
		
		response.getConfigs().putAll(keyMap);
		System.out.println(response);
	}
	
	public static Map<String, Object> filterConfig(List<AppVersionConfig> configList) {
		Map<String, Object> keyMap = new HashMap<>();
		configList.forEach(config -> doFilter(keyMap,config));
		return keyMap;
	}
	
	public static Map<String, Object> filterGlobalConfig(Map<String, Object> keyMap, List<AppVersionConfig> globalConfigList) {
		Map<String, Object> globalKeyMap = new HashMap<>();
		
		globalConfigList.forEach(config -> {
				if(!keyMap.containsKey(config.getKey())) { 
					doFilter(globalKeyMap,config); 
					}
				});
		return globalKeyMap;
	}
	
	public static void doFilter(Map<String, Object> keyMap, AppVersionConfig config) {
		if(!keyMap.containsKey(config.getKey())) {
			keyMap.put(config.getKey(), config.getValue());
		} else {
			Object value = keyMap.get(config.getKey());
			List<String> values = new ArrayList<>();
			if(value instanceof String) {
				values.add((String)value);
			} else {
				values.addAll((List<String>)value);
			}
			values.add(config.getValue());
			keyMap.put(config.getKey(), values);
		}
	}
	
	public static AppVersionKey getAppVersionKey(String appName, String appVersion, String platformName, String platformVersion) {
		AppVersionKey key = new AppVersionKey(appName, appVersion, platformName, platformVersion);
		key.setId(124);
		AppVersionConfig config1 = new AppVersionConfig("whitelist", "url1", "desc",key);
		AppVersionConfig config2 = new AppVersionConfig("whitelist", "url2", "desc", key);
		AppVersionConfig config3 = new AppVersionConfig("contact", "1312321", "desc", key);
		AppVersionConfig config6 = new AppVersionConfig("contact", "1312321", "desc", key);
		AppVersionConfig config4 = new AppVersionConfig( "email", "abc@abc.com", "desc", key);
		AppVersionConfig config5 = new AppVersionConfig("whitelist", "url2", "desc", key);
		
		List<AppVersionConfig> configs = new ArrayList<>();
		configs.add(config1);
		configs.add(config2);
		configs.add(config3);
		configs.add(config4);
		configs.add(config5);
		configs.add(config6);
		
		key.setConfigs(configs);
		
		return key;
	}
	
	public static AppVersionKey getGlobalAppVersionKey(String appName) {
		AppVersionKey global = new AppVersionKey(appName, "global", "global", "global");
		global.setId(125);
		AppVersionConfig config5 = new AppVersionConfig("whitelist", "url2", "desc", global);
		AppVersionConfig config7 = new AppVersionConfig("blacklist", "url22", "desc", global);
		AppVersionConfig config8 = new AppVersionConfig("blacklist", "url23", "desc", global);
		AppVersionConfig config6 = new AppVersionConfig("contact", "1312321", "desc", global);
		List<AppVersionConfig> globalConfigs = new ArrayList<>();
		globalConfigs.add(config5);
		globalConfigs.add(config6);
		globalConfigs.add(config7);
		globalConfigs.add(config8);
		
		global.setConfigs(globalConfigs);
		return global;
	}
}
