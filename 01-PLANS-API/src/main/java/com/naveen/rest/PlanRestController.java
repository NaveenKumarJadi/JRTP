package com.naveen.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.constants.AppConstants;
import com.naveen.entity.Plan;
import com.naveen.props.AppProperties;
import com.naveen.service.PlanService;

@RestController
public class PlanRestController {

//	@Autowired
	private PlanService planService;

//	@Autowired // we are using constructor injection so we are removing
	private AppProperties appProperties;
	
	private Map<String, String> messages;
//	Map<String, String> messages = appProperties.getMessages();
	
	//no need to write @Autowired annotation for constructor injection, if our class contain only one constructor
	public PlanRestController(PlanService planService, AppProperties appProperties) {
		this.planService = planService;
		this.messages = appProperties.getMessages();
//		System.out.println(this.messages);
	}
	
	@GetMapping("/categories")
	public ResponseEntity<Map<Integer, String>> planCategories() {

		Map<Integer, String> categories = planService.getPlanCategories();

		return new ResponseEntity<Map<Integer, String>>(categories, HttpStatus.OK);
	}

	@PostMapping("/plan")
	public ResponseEntity<String> savePlan(@RequestBody Plan plan) {

		String responseMsg = AppConstants.EMPTY_STR;
		boolean isSaved = planService.savePlan(plan);

//		Map<String, String> messages = appProperties.getMessages(); // we are using at class level in constructor

		if (isSaved) {
//			String planSaveScc = messages.get("planSaveScc");
//			responseMsg = planSaveScc;
//			responseMsg = "Plan Saved";
			responseMsg = messages.get(AppConstants.PLAN_SAVE_SUCC);
		} else {
			responseMsg = messages.get(AppConstants.PLAN_SAVE_FAIL);
//			responseMsg = "Plan Not Saved";
		}
		return new ResponseEntity<String>(responseMsg, HttpStatus.CREATED);
	}

	@GetMapping("/getAllPlans")
	public ResponseEntity<List<Plan>> plans() {
		List<Plan> allPlans = planService.getAllPlans();
		return new ResponseEntity<List<Plan>>(allPlans, HttpStatus.OK);
	}

	@PostMapping("/plan/{planId}")
	public ResponseEntity<Plan> editPlan(@PathVariable Integer PlanId) {
		Plan plan = planService.getPlanById(PlanId);
		return new ResponseEntity<Plan>(plan, HttpStatus.OK);
	}

	@PutMapping("/plan")
	public ResponseEntity<String> updatePlan(@RequestBody Plan plan){
		
		boolean isUpdated = planService.updatePlan(plan);
		
		String msg = AppConstants.EMPTY_STR;
		
//		Map<String, String> messages = appProperties.getMessages();
		
		if(isUpdated) {
//			msg = "Plan Updated";
			msg = messages.get(AppConstants.PLAN_UPDATE_SUCC);
		} else {
//			msg = "Plan Not Updated";
			msg = messages.get(AppConstants.PLAN_UPDATE_FAIL);
		}
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	@DeleteMapping("/plan/{planId}")
	public ResponseEntity<String> deletePlan(@PathVariable Integer PlanId){
		
		boolean isDeleted = planService.deletePlanById(PlanId);
		
		String msg = AppConstants.EMPTY_STR;
		
//		Map<String, String> messages = appProperties.getMessages();
		
		if(isDeleted) {
//			msg = "Plan Deleted";
			msg = messages.get(AppConstants.PLAN_DELETE_SUCC);
		}else {
//			msg = "Plan Not Deleted";
			msg = messages.get(AppConstants.PLAN_DELETE_FAIL);
		}
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	@PutMapping("/status-change/{planId}/{status}")
	public ResponseEntity<String> statusChange(@PathVariable Integer planId, @PathVariable String Status){
		
		String msg = AppConstants.EMPTY_STR;
		
		boolean isStatusChanged = planService.planStatusChange(planId, Status);

//		Map<String, String> messages = appProperties.getMessages();
		
		if(isStatusChanged) {
//			msg = "Status Changed";
			msg = messages.get(AppConstants.PLAN_STATUS_CHANGE);
		} else {
//			msg = "Status Not Changed";
			msg = messages.get(AppConstants.PLAN_STATUS_CHANGE_FAIL);
		}
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
}
