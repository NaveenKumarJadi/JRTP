package com.naveen.service;

import com.naveen.binding.EligResponse;

public interface EligService {

	public EligResponse determineEligibility(Long caseNum);
	
}
