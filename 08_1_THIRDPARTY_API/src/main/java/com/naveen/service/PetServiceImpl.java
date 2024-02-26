package com.naveen.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naveen.entity.Pet;
import com.naveen.repo.PetRepo;

@Service
public class PetServiceImpl implements PetService {

	@Autowired
	public PetRepo petRepo;

	@Override
	public String savePet(Pet pet) {
		petRepo.save(pet);
		return "saved successfully";
	}

	@SuppressWarnings("deprecation")
	@Override
	public Pet getPetById(int id) {

		Optional<Pet> findById = petRepo.findById(id);
		if(findById.isPresent()) {
			return findById.get();
		}
		return null;

	}

}
