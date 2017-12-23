package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AppVersionConfig;

public interface AppVersionConfigRepository extends JpaRepository<AppVersionConfig, Long> {

}
