package com.naveen.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.binding.PlanSelection;
import com.naveen.service.DcService;

@RestController
public class PlanSelectionRestController {

	@Autowired
	private DcService service;
	
	@PostMapping("/planSelection")
	public ResponseEntity<Long> planSelection(@RequestBody PlanSelection planSelection) {
		
		Long caseNum = service.savePlanSelection(planSelection);
		
		return new ResponseEntity<>(caseNum, HttpStatus.CREATED);
	}
	
	
}
