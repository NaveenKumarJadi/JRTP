package com.naveen.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.entity.PlanEntity;

public interface PlanRepository extends JpaRepository<PlanEntity, Serializable> {

}
