package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.AppVersionKey;

public interface AppVersionKeyRepository extends JpaRepository<AppVersionKey, Long> {
	AppVersionKey findByAppNameAndAppVersionAndPlatformNameAndPlatformVersion(String appName, String appVersion, String platformName, String platformVersion);
}
