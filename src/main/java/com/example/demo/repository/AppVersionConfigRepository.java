	package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.AppVersionConfig;
import com.example.demo.model.AppVersionKey;

public interface AppVersionConfigRepository extends JpaRepository<AppVersionConfig, Long> {
	@Query(value="SELECT * FROM APP_VERSION_CONFIG C JOIN APP_VERSION_KEY K ON K.id = C.APP_VERSION_KEY_ID  WHERE K.APP_NAME = :#{#key.appName} AND K.APP_VERSION =:#{#key.appVersion} AND K.PLATFORM_NAME=:#{#key.platformName} AND K.PLATFORM_VERSION=:#{#key.platformVersion}", nativeQuery=true)
	List<AppVersionConfig> findByAppVersionKey(@Param("key") AppVersionKey key);
	
	@Query(value="SELECT * FROM APP_VERSION_CONFIG C JOIN APP_VERSION_KEY K ON K.id = C.APP_VERSION_KEY_ID  WHERE K.APP_NAME IN :#{#apps} AND K.PLATFORM_NAME IN :#{#platforms} AND K.APP_VERSION IN :#{#appVersions} AND K.PLATFORM_VERSION  IN :#{#platformVersions}", nativeQuery=true)
	List<AppVersionConfig> findByAppVersionKeyAppNameInAndAppVersionKeyPlatformNameInAndAppVersionKeyAppVersionInAndAppVersionKeyPlatformNameIn(@Param("apps") List<String> apps, @Param("platforms") List<String> platforms, @Param("appVersions") List<String> appVersions, @Param("platformVersions") List<String> platformVersions);
}
