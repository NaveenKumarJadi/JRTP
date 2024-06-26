package com.naveen.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.entity.DcCaseEntity;

public interface DcCaseRepo extends JpaRepository<DcCaseEntity, Serializable> {

	public DcCaseEntity findByAppId(Integer appId);
	
	
}
