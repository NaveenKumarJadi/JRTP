package com.naveen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WelcomeRestController {

	@Autowired
	private GreetClient greetClient;
	
	@GetMapping("/welcome")
	public WelcomeResponse getWelcomeMsg() {
//	public String getWelcomeMsg() { //if you use Inter-Service communication
	
		String welcomeMsg = "Welcome to Ayodhya..";
		
		//Inter-Service communication
		String greetMsg = greetClient.invokeGreetApi();
		
		//External-Service communication
		RestTemplate rt = new RestTemplate();
		String petEndpointUrl = "http://localhost:9095/pet/1";
		ResponseEntity<Pet> entity = rt.getForEntity(petEndpointUrl, Pet.class);
		Pet petData = entity.getBody();

		WelcomeResponse finalResponse = new WelcomeResponse();
		finalResponse.setGreetMsg(greetMsg);
		finalResponse.setWelcomeMsg(welcomeMsg);
		finalResponse.setPet(petData);

		return finalResponse;
		
//		return greetMsg + ", " + welcomeMsg;
//		From API-GATEWAY :: http:localhost:3333/welcome
		
	}
}
