package com.naveen.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.naveen.bindings.ActivateAccount;
import com.naveen.bindings.Login;
import com.naveen.bindings.User;
import com.naveen.entity.UserMaster;
import com.naveen.repo.UserMasterRepo;
import com.naveen.utils.EmailUtils;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	private Logger logger = LoggerFactory.getLogger(UserMgmtServiceImpl.class);
			
	@Autowired
	private UserMasterRepo userMasterRepo;
	
	@Autowired
	private EmailUtils emailUtils;

	private Random random = new Random();
	
	@Override
	public boolean saveUser(User user) {

		UserMaster entity = new UserMaster();
		BeanUtils.copyProperties(user, entity);

		entity.setPassword(generateRandomPwd());
		entity.setAccStatus("In-Active");

		UserMaster save = userMasterRepo.save(entity);

		// TODO: send Registration Email
		String subject = "Your Registration Suceess";
		String filename= "REG-EMAIL-BODY.txt";
		String body = readEmailBody(entity.getFullName(), entity.getPassword(), filename);
		
		emailUtils.sendEmail(user.getEmail(), subject, body);

		return save.getUserId() != null; // no need to write if and else conditions
	}

	@Override
	public boolean activateUser(ActivateAccount activateAccount) {

		UserMaster entity = new UserMaster();
		entity.setEmail(activateAccount.getEmail());
		entity.setPassword(activateAccount.getTempPwd());

		Example<UserMaster> of = Example.of(entity); // it is prepare the query by where clause ->> "select * from
														// user_master where email=? and pwd=?"

		List<UserMaster> findAll = userMasterRepo.findAll(of);

		if (findAll.isEmpty()) {
			return false;
		} else {
			UserMaster userMaster = findAll.get(0);
			userMaster.setPassword(activateAccount.getNewPwd());
			userMaster.setAccStatus("Active");
			userMasterRepo.save(userMaster);
			return true;
		}
	}

	@Override
	public List<User> getAllUsers() {

		List<UserMaster> findAll = userMasterRepo.findAll();

		List<User> users = new ArrayList<>();
		for (UserMaster entity : findAll) {
			User user = new User();
			BeanUtils.copyProperties(entity, user);
			users.add(user);
		}
		return users;
	}

	@Override
	public User getUserById(Integer userId) {

		Optional<UserMaster> findById = userMasterRepo.findById(userId);

		if (findById.isPresent()) {
			User user = new User();
			UserMaster userMaster = findById.get();
			BeanUtils.copyProperties(userMaster, user);
			return user;
		}
		return null;
	}


	@Override
	public boolean deleteUserById(Integer userId) {

		try {
			userMasterRepo.deleteById(userId);
			return true;
		} catch (Exception e) {
//			e.printStackTrace();
			logger.error("Exception Occured", e);
		}
		return false;
	}

	@Override
	public boolean changeAccountStatus(Integer UserId, String accStatus) {

		Optional<UserMaster> findById = userMasterRepo.findById(UserId);

		if (findById.isPresent()) {
			UserMaster userMaster = findById.get();
			userMaster.setAccStatus(accStatus);
			return true;
		}
		return false;
	}

	@Override
	public String login(Login login) {
		/*
		UserMaster entity = new UserMaster();
		entity.setEmail(login.getEmail());
		entity.setPassword(login.getPassword());
		
		Example<UserMaster> of = Example.of(entity);
		List<UserMaster> findAll = userMasterRepo.findAll(of);
		
		if(findAll.isEmpty()) {
			return "Invalid Credentials";
		}else {
			UserMaster userMaster = findAll.get(0);
			if(userMaster.getAccStatus().equals("Active")) {
				return "SUCCESS";
			}else {
				return "Account not activated";
			}
		}
		*/
		
		UserMaster entity = userMasterRepo.findByEmailAndPassword(login.getEmail(), login.getPassword());

		if (entity == null) {
			return "Invalid Credentials";
		}

		if (entity.getAccStatus().equals("Active")) {
			return "SUCCESS";
		} else {
			return "Account not activated";
		}
	}

	@Override
	public String forgotPwd(String email) {

		UserMaster entity = userMasterRepo.findByEmail(email);

		if (entity == null) {
			return "Invalid Email";

		}

		// TODO: Send Pwd to user in email
		String subject = "Forgot Password";
		String fileName = "RECOVER-PWD-BODY.txt";
		String body = readEmailBody(entity.getFullName(), entity.getPassword(), fileName);

		boolean sendEmail = emailUtils.sendEmail(email, subject, body);

		if (sendEmail) {
			return "Password sent to your registered email";
		}

		return null;
	}

	private String generateRandomPwd() {

		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

		StringBuilder sb = new StringBuilder();
//		Random random = new Random(); // we are creating random object as variable at class level
		int length = 6;
		for (int i = 0; i < length; i++) {
			int index = this.random.nextInt(alphaNumeric.length());
			char randomChar = alphaNumeric.charAt(index);
			sb.append(randomChar);
		}
		return sb.toString();
	}

	private String readEmailBody(String fullname, String pwd, String filename) {
		
//		String filename = "REG-EMAIL-BODY.txt"; // we are passing dynamically so no need this
		String url = "";
		String mailBody = null;
		try (
				FileReader fr = new FileReader(filename);
				BufferedReader br = new BufferedReader(fr);
		) {
//			StringBuffer buffer = new StringBuffer(); // it is synchronized
			StringBuilder buffer = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				buffer.append(line); // pocess the data
				line = br.readLine();
			}
//			br.close(); // we are using try with resources so no need to close it will close automatically (1.7V)
			mailBody = buffer.toString();
			mailBody = mailBody.replace("{FULLNAME}", fullname);
			mailBody = mailBody.replace("{TEMP-PWD}", pwd);
			mailBody = mailBody.replace("{URL}", url);
			mailBody = mailBody.replace("PWD", pwd);
		} catch (Exception e) {
//			e.printStackTrace();
			logger.error("Exception Occured", e);
		}
		return mailBody;
	}
}
