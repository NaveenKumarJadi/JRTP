package com.naveen.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.entity.EligDtsEntity;

public interface EligDtlsRepository extends JpaRepository<EligDtsEntity, Serializable> {

	public EligDtsEntity findByCaseNum(long caseNum);
	
}
