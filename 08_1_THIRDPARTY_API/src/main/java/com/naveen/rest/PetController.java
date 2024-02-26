package com.naveen.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.entity.Pet;
import com.naveen.service.PetService;

@RestController
public class PetController {

	@Autowired
	public PetService petService;

	@PostMapping("/pet")
	public ResponseEntity<String> savePet(@RequestBody Pet pet) {
		
		String savePet = petService.savePet(pet);

		return new ResponseEntity<String>(savePet, HttpStatus.CREATED);
	}

	@GetMapping("/pet/{id}")
	public ResponseEntity<Pet> getPetById(@PathVariable int id) {

		Pet petById = petService.getPetById(id);

		return new ResponseEntity<>(petById, HttpStatus.OK);
	}

}
