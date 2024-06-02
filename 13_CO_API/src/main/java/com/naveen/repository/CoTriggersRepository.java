package com.naveen.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.entity.CoTrigerEntity;

public interface CoTriggersRepository extends JpaRepository<CoTrigerEntity, Serializable>{

	public List<CoTrigerEntity> findByTrgStatus(String status);

	public CoTrigerEntity findByCaseNum(Long caseNum);
	
}
