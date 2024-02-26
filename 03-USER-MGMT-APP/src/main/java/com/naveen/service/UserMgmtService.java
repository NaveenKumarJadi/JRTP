package com.naveen.service;

import java.util.List;

import com.naveen.bindings.ActivateAccount;
import com.naveen.bindings.Login;
import com.naveen.bindings.User;

public interface UserMgmtService {

	public boolean saveUser(User user);
	
	public boolean activateUser(ActivateAccount activateAccount);
	
	public List<User> getAllUsers();
	
	public User getUserById(Integer userId);
		
	public boolean deleteUserById(Integer userId);
	
	public boolean changeAccountStatus(Integer UserId, String accStatus);
	
	public String login(Login login);

	public String forgotPwd(String email);
	
}
