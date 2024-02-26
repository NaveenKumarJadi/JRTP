package com.naveen;

public class WelcomeResponse {

	private String greetMsg;
	private String welcomeMsg;
	private Pet pet;

	// getters & setters

	public String getGreetMsg() {
		return greetMsg;
	}

	public void setGreetMsg(String greetMsg) {
		this.greetMsg = greetMsg;
	}

	public String getWelcomeMsg() {
		return welcomeMsg;
	}

	public void setWelcomeMsg(String welcomeMsg) {
		this.welcomeMsg = welcomeMsg;
	}

	@Override
	public String toString() {
		return "WelcomeResponse [greetMsg=" + greetMsg + ", welcomeMsg=" + welcomeMsg + ", pet=" + pet + "]";
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

}
