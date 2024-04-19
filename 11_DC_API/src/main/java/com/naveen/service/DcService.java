package com.naveen.service;

import java.util.Map;

import com.naveen.binding.ChildRequest;
import com.naveen.binding.DcSummary;
import com.naveen.binding.Education;
import com.naveen.binding.Income;
import com.naveen.binding.PlanSelection;

public interface DcService {

	public Long loadCaseNum(Integer appId);
	
	public Map<Integer, String> getPlanNames();
	
	public Long savePlanSelection(PlanSelection planSelection);
	
	public Long saveIncomeData(Income income);
	
	public Long saveEducation(Education education);
	
	public Long saveChildrens(ChildRequest request);
	
	public DcSummary getSummary(Long caseNumber);
	
}
