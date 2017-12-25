package com.example.demo.model;

import java.util.ArrayList;
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
		if(configs == null) {
			configs = new ArrayList<>();
		}
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appName == null) ? 0 : appName.hashCode());
		result = prime * result + ((appVersion == null) ? 0 : appVersion.hashCode());
		result = prime * result + ((configs == null) ? 0 : configs.hashCode());
		result = prime * result + ((platformName == null) ? 0 : platformName.hashCode());
		result = prime * result + ((platformVersion == null) ? 0 : platformVersion.hashCode());
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
		AppVersionKey other = (AppVersionKey) obj;
		if (appName == null) {
			if (other.appName != null)
				return false;
		} else if (!appName.equals(other.appName))
			return false;
		if (appVersion == null) {
			if (other.appVersion != null)
				return false;
		} else if (!appVersion.equals(other.appVersion))
			return false;
		if (configs == null) {
			if (other.configs != null)
				return false;
		} else if (!configs.equals(other.configs))
			return false;
		if (platformName == null) {
			if (other.platformName != null)
				return false;
		} else if (!platformName.equals(other.platformName))
			return false;
		if (platformVersion == null) {
			if (other.platformVersion != null)
				return false;
		} else if (!platformVersion.equals(other.platformVersion))
			return false;
		return true;
	}
	
	public boolean matches(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppVersionKey other = (AppVersionKey) obj;
		if (appName == null) {
			if (other.appName != null)
				return false;
		} else if (!appName.equals(other.appName))
			return false;
		if (appVersion == null) {
			if (other.appVersion != null)
				return false;
		} else if (!appVersion.equals(other.appVersion))
			return false;
		if (platformName == null) {
			if (other.platformName != null)
				return false;
		} else if (!platformName.equals(other.platformName))
			return false;
		if (platformVersion == null) {
			if (other.platformVersion != null)
				return false;
		} else if (!platformVersion.equals(other.platformVersion))
			return false;
		return true;
	}
}
