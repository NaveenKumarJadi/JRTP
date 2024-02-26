package com.naveen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetRestController {

	@GetMapping("/greet")
	public String getGreetMsg() {
		String msg = "Jai Sri Ram...!";
		return msg;
	}
}
