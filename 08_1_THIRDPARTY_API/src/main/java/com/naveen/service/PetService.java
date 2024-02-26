package com.naveen.service;

import com.naveen.entity.Pet;

public interface PetService {

	public String savePet(Pet pet);
	
	public Pet getPetById(int id);
	
}
