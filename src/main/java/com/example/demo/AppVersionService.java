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
import com.example.demo.repository.AppVersionConfigRepository;
import com.example.demo.repository.AppVersionKeyRepository;

/**
 * @author Arun
 *
 */
@Service
public class AppVersionService {
	
	private static final String GLOBAL = "global";

	@Autowired
	private AppVersionKeyRepository keyRepo;
	
	@Autowired
	private AppVersionConfigRepository configRepo;
	
	public AppVersionService(AppVersionConfigRepository configRepo) {
		this.configRepo = configRepo;
	}
	
	/**
	 * @param appName
	 * @param appVersion
	 * @param platformName
	 * @param platformVersion
	 * @return
	 */
	public AppVersionResponse getConfig(String appName, String appVersion, String platformName, String platformVersion) {
//		AppVersionKey key = keyRepo.findByAppNameAndAppVersionAndPlatformNameAndPlatformVersion(appName, appVersion, platformName, platformVersion);
//		AppVersionKey global = keyRepo.findByAppNameAndAppVersionAndPlatformNameAndPlatformVersion(appName, GLOBAL, GLOBAL, GLOBAL);
//		List<AppVersionConfig> configListing = configRepo.findByAppVersionKey(new AppVersionKey(appName, appVersion, platformName, platformVersion));
//		List<AppVersionConfig> configGlobalListing = configRepo.findByAppVersionKey(new AppVersionKey(appName,  GLOBAL, GLOBAL, GLOBAL));
		
		long startTime = System.currentTimeMillis();
		AppVersionKey key = new AppVersionKey(appName, appVersion, platformName, platformVersion);
		AppVersionKey appPlatformGlobal = new AppVersionKey(appName, GLOBAL, platformName, GLOBAL);
		AppVersionKey appGlobal = new AppVersionKey(appName, GLOBAL, GLOBAL, GLOBAL);
		AppVersionKey platformGlobal = new AppVersionKey(GLOBAL, GLOBAL, platformName, GLOBAL);
		AppVersionKey global = new AppVersionKey(GLOBAL, GLOBAL, GLOBAL, GLOBAL);
		
		List<AppVersionConfig> configLists = configRepo
				.findByAppVersionKeyAppNameInAndAppVersionKeyPlatformNameInAndAppVersionKeyAppVersionInAndAppVersionKeyPlatformNameIn(
						Arrays.asList(appName), Arrays.asList(platformName, GLOBAL), Arrays.asList(appVersion, GLOBAL),
						Arrays.asList(platformVersion, GLOBAL));
		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds");
		System.out.println(configLists);
		
		List<AppVersionConfig> config = new ArrayList<>();
		List<AppVersionConfig> appPlatformGlobalConfig = new ArrayList<>();
		List<AppVersionConfig> appGlobalConfig = new ArrayList<>();
		List<AppVersionConfig> platformGlobalconfig = new ArrayList<>();
		List<AppVersionConfig> globalConfig = new ArrayList<>();
		
		/**Separating the list based on their specificity. App, Platform & their version specific, App & Platform global, App global, Platform global & global.
		 Their preference is also in the same order mentioned below.
		 eg: Specific is 			AppName: App   , AppVersion: 1.2   , PlatformName: ios   , PlatormVersion: 11.2
		     AppPlatform global is 	AppName: App   , AppVersion: global, PlatformName: ios   , PlatormVersion: global
			 App Global is 			AppName: App   , AppVersion: global, PlatformName: global, PlatormVersion: global
		     Platform Global is 		AppName: global, AppVersion: global, PlatformName: ios   , PlatormVersion: global
		     Global is				AppName: global, AppVersion: global, PlatformName: global, PlatormVersion: global
		 * *
		 */
		if(configLists != null) {
			configLists.forEach(conf -> {
					if(conf.getAppVersionKey().matches(key)) {
						config.add(conf);
					} else if(conf.getAppVersionKey().matches(appPlatformGlobal)) {
						appPlatformGlobalConfig.add(conf);
					} else if(conf.getAppVersionKey().matches(appGlobal)) {
						appGlobalConfig.add(conf);
					} else if(conf.getAppVersionKey().matches(platformGlobal)) {
						platformGlobalconfig.add(conf);
					} else if(conf.getAppVersionKey().matches(global)) {
						globalConfig.add(conf);
					}
			});
		}
		
		/** Order of the parameters impacts the logic (which gets processed first). Don't change the Order.*/
		AppVersionResponse response = processConfig(config, appPlatformGlobalConfig, appGlobalConfig, platformGlobalconfig, globalConfig);
		
		return response;
	}
	
	/**
	 * Method takes in a List<List<AppVersionConfig>> as varargs.
	 * 
	 *  Foreach of List<AppVersionConfig> - Calls the filter Global config and adds returned value to AppVersionResponse.
	 *  Order of the Params is extremely important here as omission of Configs happen based on that.
	 *  
	 * @param configs
	 * @return
	 */
	@SafeVarargs
	public final AppVersionResponse processConfig(List<AppVersionConfig> ... configs) {
		AppVersionResponse response = new AppVersionResponse();
		Arrays.asList(configs).forEach(configlist -> {
			Map<String,Object> globalKeyMap = filterGlobalConfig(response.getConfigs(), configlist);
			response.getConfigs().putAll(globalKeyMap);
			}
		);
		return response;
	}

	
	/**
	 * @param configList
	 * @return
	 */
	public Map<String, Object> filterConfig(List<AppVersionConfig> configList) {
		Map<String, Object> keyMap = new HashMap<>();
		if(configList != null) {
			configList.forEach(config -> doFilter(keyMap,config));
		}
		return keyMap;
	}
	
	/**
	 * Method takes in a KeyMap and list of AppVersionConfig, checks whether the App Specific AppVersionConfig exists in the KeyMap,
	 * if already exists, omits that AppVersion Global config. [Because the App Specific config takes precedence over the global config] 
	 * if NOT, add a new key value pair into the map for that config.
	 * 
	 * eg: App specific config - key: whitelist and value: 'xxx'  and
	 *     Global Config - key: whitelist and value: 'yyy'
	 *                   - key: contact and value: '1234'
	 * Then whitelist at the global level gets omitted whereas contact gets added to the response.
	 * 
	 * @param keyMap
	 * @param globalConfigList
	 * @return
	 */
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
	
	/**
	 * Method takes in an AppVersionConfigRecord, parses it and adds them as key value pair into a Map.
	 * If more than one value exists for a key, this will convert the values into a List.
	 * @param keyMap
	 * @param config
	 */
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

	/**
	 * Method for adding data into the database.
	 * @param keys
	 * @return
	 */
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
