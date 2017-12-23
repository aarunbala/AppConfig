package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AppVersionConfig {
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	private String key;
	private String value;
	private String description;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appVersionKey_id")
    private AppVersionKey appVersionKey;
	
	public AppVersionConfig() {
		super();
	}
	
	public AppVersionConfig(String key, String value, String description, AppVersionKey appVersionKey) {
		super();
		this.key = key;
		this.value = value;
		this.description = description;
		this.appVersionKey = appVersionKey;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public AppVersionKey getAppVersionKey() {
		return appVersionKey;
	}
	public void setAppVersionKey(AppVersionKey appVersionKey) {
		this.appVersionKey = appVersionKey;
	}

	@Override
	public String toString() {
		return "AppVersionConfig [key=" + key + ", value=" + value + ", description=" + description + ", appVersionKey="
				+ appVersionKey.getId() + "]";
	}

}
