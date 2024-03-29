package com.naveen.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.bindings.ActivateAccount;
import com.naveen.bindings.Login;
import com.naveen.bindings.User;
import com.naveen.service.UserMgmtService;

@RestController
public class UserRestController {

	@Autowired
	private UserMgmtService service;

	@PostMapping("/user")
	public ResponseEntity<String> userReg(@RequestBody User user) {

		boolean saveUser = service.saveUser(user);
		if (saveUser) {
			return new ResponseEntity<String>("Registration Success", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Registration Failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/activate")
	public ResponseEntity<String> activateAccount(@RequestBody ActivateAccount acc) {

		boolean isActivated = service.activateUser(acc);
		if (isActivated) {
			return new ResponseEntity<String>("Account Activated", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Invalid Temporary Pwd", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {

		List<User> allUsers = service.getAllUsers();
		return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
	}

	@GetMapping("/user/{UserId}")
	public ResponseEntity<User> getUserById(@PathVariable Integer userId) {

		User user = service.getUserById(userId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@DeleteMapping("/user/{userId}")
	public ResponseEntity<String> deleteUserById(@PathVariable Integer userId) {

		boolean isDeleted = service.deleteUserById(userId);
		if (isDeleted) {
			return new ResponseEntity<String>("Deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/status/{userId}/{status}")
	public ResponseEntity<String> statusChange(@PathVariable Integer userId, @PathVariable String status) {

		boolean isChanged = service.changeAccountStatus(userId, status);
		if (isChanged) {
			return new ResponseEntity<String>("Status Chagned", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Failed to Chagned", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Login login) {

		String status = service.login(login);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}

	@GetMapping("/forgotPwd/{email}")
	public ResponseEntity<String> forgotPwd(@PathVariable String email) {

		String status = service.forgotPwd(email);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}

}
