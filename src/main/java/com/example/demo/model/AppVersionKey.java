package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="app_version_key")
public class AppVersionKey {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	private String appName;
	private String appVersion;
	private String platformName;
	private String platformVersion;
	
	@OneToMany(mappedBy = "appVersionKey", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<AppVersionConfig> configs;
	
	public AppVersionKey() {
		super();
	}
	public AppVersionKey(String appName, String appVersion, String platformName, String platformVersion) {
		super();
		this.appName = appName;
		this.appVersion = appVersion;
		this.platformName = platformName;
		this.platformVersion = platformVersion;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public String getPlatformVersion() {
		return platformVersion;
	}
	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}
	public List<AppVersionConfig> getConfigs() {
		return configs;
	}
	public void setConfigs(List<AppVersionConfig> configs) {
		this.configs = configs;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "AppVersionKey [appName=" + appName + ", appVersion=" + appVersion + ", platformName=" + platformName
				+ ", platformVersion=" + platformVersion + ", configs=" + configs + "]";
	}
	
}
