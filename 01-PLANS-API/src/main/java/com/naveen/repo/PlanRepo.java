package com.naveen.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.entity.Plan;

public interface PlanRepo extends JpaRepository<Plan, Integer> {

}
