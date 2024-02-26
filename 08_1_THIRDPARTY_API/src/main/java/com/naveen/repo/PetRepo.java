package com.naveen.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.entity.Pet;

public interface PetRepo extends JpaRepository<Pet, Serializable> {

}
