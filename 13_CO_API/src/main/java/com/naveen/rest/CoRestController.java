package com.naveen.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.binding.CoResponse;
import com.naveen.services.CoService;

@RestController
public class CoRestController {

	@Autowired
	private CoService coService;
	
	@GetMapping("/process")
	public CoResponse porcessTriggers() {
		return coService.processPendingTriggers(); 
	}
	
}
