package com.naveen.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.entity.CitizenAppEntity;

public interface CitizenAppRepository extends JpaRepository<CitizenAppEntity, Serializable>{

}
