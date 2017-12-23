package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AppVersionKey;

@RestController 
public class AppVersionController {

	@Autowired
	AppVersionService service;
	
	@GetMapping("/config/{appName}/{appVersion}/{platformName}/{platformVersion}")
	public AppVersionResponse getConfig(@PathVariable String appName,@PathVariable String appVersion,@PathVariable String platformName,@PathVariable String platformVersion) {
		return service.getConfig(appName, appVersion, platformName, platformVersion);
	}
	
	@PostMapping("/addConfig")
	public List<AppVersionResponse> addConfigs(@RequestBody List<AppVersionKey> keys) {
		return service.addConfig(keys);
	}
}
