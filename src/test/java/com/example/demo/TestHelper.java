package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.demo.model.AppVersionConfig;
import com.example.demo.model.AppVersionKey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestHelper {

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
	
	public static List<AppVersionConfig> getAppVersionKeyConfigs(String appName, String appVersion, String platformName, String platformVersion) {
		List<AppVersionConfig> configs = new ArrayList<>();
		configs.addAll(getAppVersionKey(appName, appVersion, platformName, platformVersion).getConfigs());
		configs.addAll(getGlobalAppVersionKeyConfigs(appName));
		return configs;
	}
	
	public static List<AppVersionConfig> getAppVersionKeyConfigsWithNoGlobal(String appName, String appVersion, String platformName, String platformVersion) {
		List<AppVersionConfig> configs = new ArrayList<>();
		configs.addAll(getAppVersionKey(appName, appVersion, platformName, platformVersion).getConfigs());
		return configs;
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
	
	public static List<AppVersionConfig> getGlobalAppVersionKeyConfigs(String appName) {
		return getGlobalAppVersionKey(appName).getConfigs();
	}
	
	public static AppVersionKey getEmptyGlobalAppVersionKey(String appName) {
		AppVersionKey global = new AppVersionKey(appName, "global", "global", "global");
		List<AppVersionConfig> globalConfigs = new ArrayList<>();
		
		global.setConfigs(globalConfigs);
		return global;
	}
	
	public static List<String> paramAsList(String ... values) {
		return Arrays.asList(values);
	}
	
	public static Map<String, Object> getKeyMap() {
		Map<String, Object> keyMap = new HashMap<>();
		keyMap.put("whitelist", paramAsList("url1", "url2", "url2"));
		keyMap.put("contact", paramAsList("1312321","1312321"));
		keyMap.put("email", "abc@abc.com");
		return keyMap;
	}
	
	public static Map<String, Object> getGlobalKeyMap() {
		Map<String, Object> keyMap = new HashMap<>();
		keyMap.put("blacklist", paramAsList("url22", "url23"));
		return keyMap;
	}
	
	public static Map<String, Object> getGlobalKeyMapForEmptyKeyMap() {
		Map<String, Object> keyMap = new HashMap<>();
		keyMap.put("blacklist", paramAsList("url22", "url23"));
		keyMap.put("whitelist", "url2");
		keyMap.put("contact", "1312321");
		return keyMap;
	}
	
	public static AppVersionResponse getAppVersionResponse() {
		AppVersionResponse response = new AppVersionResponse();
		response.getConfigs().put("whitelist", paramAsList("url2", "url1","url2"));
		response.getConfigs().put("contact", paramAsList("1312321", "1312321"));
		response.getConfigs().put("email", "abc@abc.com");
		response.getConfigs().put("blacklist", paramAsList("url22", "url23"));
		return response;
	}
	
	public static AppVersionResponse getAppVersionResponse_WithNoGlobalConfig() {
		AppVersionResponse response = new AppVersionResponse();
		response.getConfigs().put("whitelist", paramAsList("url2", "url1","url2"));
		response.getConfigs().put("contact", paramAsList("1312321", "1312321"));
		response.getConfigs().put("email", "abc@abc.com");
		return response;
	}
	
	public static JSONObject getAppVersionResponseJSON()  {
		ObjectMapper mapper = new ObjectMapper();
		JSONObject json = new JSONObject();
		try {
			json = new JSONObject(mapper.writeValueAsString(getAppVersionResponse()));
		} catch (JsonProcessingException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public static List<AppVersionConfig> getEmptyGlobalAppVersionKeyConfig(String string) {
		return new ArrayList<>();
	}
}
