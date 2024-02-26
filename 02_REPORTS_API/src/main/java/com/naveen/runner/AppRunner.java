package com.naveen.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.naveen.entity.EligibilityDetails;
import com.naveen.repo.EligibilityDetailsRepo;

@Component
public class AppRunner implements ApplicationRunner {

	@Autowired
	private EligibilityDetailsRepo repo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		EligibilityDetails entity1 = new EligibilityDetails();
		entity1.setEligId(1);
		entity1.setName("John");
		entity1.setMobile((long) 123456);
		entity1.setGender('M');
		entity1.setSsn((long) 741);
		entity1.setPlanName("SNAP");
		entity1.setPlanStatus("Approved");
		
		repo.save(entity1);
		
		EligibilityDetails entity2 = new EligibilityDetails();
		entity2.setEligId(2);
		entity2.setName("Smith");
		entity2.setMobile((long) 123457);
		entity2.setGender('M');
		entity2.setSsn((long) 852);
		entity2.setPlanName("CCAP");
		entity2.setPlanStatus("Denied");
		
		repo.save(entity2);
		
		EligibilityDetails entity3 = new EligibilityDetails();
		entity3.setEligId(3);
		entity3.setName("Robert");
		entity3.setMobile((long) 123458);
		entity3.setGender('M');
		entity3.setSsn((long) 963);
		entity3.setPlanName("Medicaid");
		entity3.setPlanStatus("Closed");
		
		repo.save(entity3);
		
	}

}
