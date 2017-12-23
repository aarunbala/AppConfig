package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AppVersionConfig;
import com.example.demo.model.AppVersionKey;
import com.example.demo.repository.AppVersionKeyRepository;

@Service
public class AppVersionService {
	
	private static final String GLOBAL = "global";

	@Autowired
	private AppVersionKeyRepository keyRepo;
	
	public AppVersionService(AppVersionKeyRepository keyRepo) {
		this.keyRepo = keyRepo;
	}
	
	public AppVersionResponse getConfig(String appName, String appVersion, String platformName, String platformVersion) {
		AppVersionKey key = keyRepo.findByAppNameAndAppVersionAndPlatformNameAndPlatformVersion(appName, appVersion, platformName, platformVersion);
		AppVersionKey global = keyRepo.findByAppNameAndAppVersionAndPlatformNameAndPlatformVersion(appName, GLOBAL, GLOBAL, GLOBAL);
		AppVersionResponse response = new AppVersionResponse();
		Map<String, Object> keyMap = new HashMap<>();
		if(key != null) {
			keyMap = filterConfig(key.getConfigs());
			response.getConfigs().putAll(keyMap);
		}
		if(global != null) {
			Map<String,Object> globalKeyMap = filterGlobalConfig(response.getConfigs(), global.getConfigs());
			response.getConfigs().putAll(globalKeyMap);
		}
		return response;
	}
	
	public List<String> paramAsList(String ... values) {
		return Arrays.asList(values);
	}

	public Map<String, Object> filterConfig(List<AppVersionConfig> configList) {
		Map<String, Object> keyMap = new HashMap<>();
		if(configList != null) {
			configList.forEach(config -> doFilter(keyMap,config));
		}
		return keyMap;
	}
	
	public Map<String, Object> filterGlobalConfig(Map<String, Object> keyMap, List<AppVersionConfig> globalConfigList) {
		Map<String, Object> globalKeyMap = new HashMap<>();
		if(globalConfigList != null) {		
			globalConfigList.forEach(config -> {
					if(!keyMap.containsKey(config.getKey())) { 
						doFilter(globalKeyMap,config); 
						}
					});
		}
		return globalKeyMap;
	}
	
	@SuppressWarnings("unchecked")
	public void doFilter(Map<String, Object> keyMap, AppVersionConfig config) {
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

	public List<AppVersionResponse> addConfig(List<AppVersionKey> keys) {
		List<AppVersionResponse> responses = new ArrayList<>();
		if(keys != null) {
			keys.forEach(key -> {
					key.getConfigs().forEach(config -> config.setAppVersionKey(key));
					keyRepo.save(key);
					responses.add(getConfig(key.getAppName(), key.getAppVersion(), key.getPlatformName(), key.getPlatformVersion()));
				}
			);
		}
		return responses;
	}

}
