package com.naveen.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.entity.CoTrigerEntity;

public interface CoTriggersRepository extends JpaRepository<CoTrigerEntity, Serializable>{

}
