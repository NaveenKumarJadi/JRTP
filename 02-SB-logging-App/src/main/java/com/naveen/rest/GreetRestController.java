package com.naveen.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetRestController {

	Logger logger = LoggerFactory.getLogger(GreetRestController.class);
	
	@GetMapping("/greet")
	public String getGreetMsg() {
		
		logger.info("getGreetMsg() execution started...!");
		
		String msg = "Good Evening...!";
		
		logger.info("getGreetMsg() execution ended...!");

		return msg;
	}
}
