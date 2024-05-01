package com.naveen.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naveen.binding.EligResponse;
import com.naveen.entity.CitizenAppEntity;
import com.naveen.entity.CoTrigerEntity;
import com.naveen.entity.DcCaseEntity;
import com.naveen.entity.DcChildrenEntity;
import com.naveen.entity.DcEducationEntity;
import com.naveen.entity.DcIncomeEntity;
import com.naveen.entity.EligDtsEntity;
import com.naveen.entity.PlanEntity;
import com.naveen.repository.CitizenAppRepository;
import com.naveen.repository.CoTriggersRepository;
import com.naveen.repository.DcCaseRepo;
import com.naveen.repository.DcChildrenRepo;
import com.naveen.repository.DcEducationRepo;
import com.naveen.repository.DcIncomeRepo;
import com.naveen.repository.EligDtlsRepository;
import com.naveen.repository.PlanRepository;

@Service
public class EligServiceImpl implements EligService {

	@Autowired
	private DcCaseRepo dcCaseRepo;

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private DcIncomeRepo incomeRepo;

	@Autowired
	private DcChildrenRepo childrenRepo;

	@Autowired
	private CitizenAppRepository citizenAppRepository;

	@Autowired
	private DcEducationRepo educationRepo;

	@Autowired
	private EligDtlsRepository eligDtlsRepository;

	@Autowired
	private CoTriggersRepository coTriggersRepository;

	@Override
	public EligResponse determineEligibility(Long caseNum) {

		Optional<DcCaseEntity> caseEntity = dcCaseRepo.findById(caseNum);
		Integer planId = null;
		String planName = null;
		Integer appId = null;

		if (caseEntity.isPresent()) {
			DcCaseEntity dcCaseEntity = caseEntity.get();
			planId = dcCaseEntity.getPlanId();
			appId = dcCaseEntity.getAppId();
		}

		Optional<PlanEntity> planEntity = planRepository.findById(planId);
		if (planEntity.isPresent()) {
			PlanEntity plan = planEntity.get();
			planName = plan.getPlanName();
		}

		Optional<CitizenAppEntity> app = citizenAppRepository.findById(appId);
		Integer age = 0;
		CitizenAppEntity citizenAppEntity = null;

		if (app.isPresent()) {
			citizenAppEntity = app.get();
			LocalDate dob = citizenAppEntity.getDob();
			LocalDate now = LocalDate.now();
			age = Period.between(dob, now).getYears();
		}

		EligResponse eligResponse = executePlanConditions(caseNum, planName, age);

		// logic to store data in db
		EligDtsEntity eligDtsEntity = new EligDtsEntity();
		BeanUtils.copyProperties(eligResponse, eligDtsEntity);

		eligDtsEntity.setCaseNum(caseNum);
		eligDtsEntity.setHolderName(citizenAppEntity.getFullname());
		eligDtsEntity.setHolderSsn(citizenAppEntity.getSsn());

		eligDtlsRepository.save(eligDtsEntity);

		CoTrigerEntity coTrigerEntity = new CoTrigerEntity();
		coTrigerEntity.setCaseNum(caseNum);
		coTrigerEntity.setTrgStatus("pending");

		coTriggersRepository.save(coTrigerEntity);

		return eligResponse;
	}

	private EligResponse executePlanConditions(Long caseNum, String planName, Integer age) {

		EligResponse response = new EligResponse();
		response.setPlanName(planName);

		// logic to execute conditions
		DcIncomeEntity income = incomeRepo.findByCaseNum(caseNum);

		if ("SNAP".equalsIgnoreCase(planName)) {

			Double empIncome = income.getEmpIncome();
			if (empIncome <= 300) {
				response.setPlanStatus("AP");
			} else {
				response.setPlanStatus("DN");
				response.setDenialReason("High Income");
			}

		} else if ("CCAP".equalsIgnoreCase(planName)) {

			boolean ageCondition = true;
			boolean kidsCountCondition = false;

			List<DcChildrenEntity> childs = childrenRepo.findByCaseNum(caseNum);
			if (!childs.isEmpty()) {
				kidsCountCondition = true;
				for (DcChildrenEntity entity : childs) {
					Integer childAge = entity.getChildAge();
					if (childAge > 16) {
						ageCondition = false;
						break;
					}
				}
			}
			if (income.getEmpIncome() <= 300 && kidsCountCondition && ageCondition) {
				response.setPlanStatus("AP");
			} else {
				response.setPlanStatus("DN");
				response.setDenialReason("Not satisfied business rules");
			}

		} else if ("Medicaid".equalsIgnoreCase(planName)) {

			Double empIncome = income.getEmpIncome();
			Double propertyIncome = income.getPropertyIncome();

			if (empIncome <= 300 && propertyIncome == 0) {
				response.setPlanStatus("AP");
			} else {
				response.setPlanStatus("DN");
				response.setDenialReason("High Income");
			}

		} else if ("Medicare".equalsIgnoreCase(planName)) {

//			Optional<CitizenAppEntity> app = citizenAppRepository.findById(appId);
//			if (app.isPresent()) {
//				CitizenAppEntity citizenAppEntity = app.get();
//				LocalDate dob = citizenAppEntity.getDob();
//				LocalDate now = LocalDate.now();
//				int age = Period.between(dob, now).getYears();

			if (age >= 65) {
				response.setPlanStatus("AP");
			} else {
				response.setPlanStatus("DN");
				response.setDenialReason("Age Not Matched");
			}
//			}

		} else if ("NJW".equalsIgnoreCase(planName)) {

			DcEducationEntity educationEntity = educationRepo.findByCaseNum(caseNum);
			Integer graduationYear = educationEntity.getGraduationYear();
			int currentYear = LocalDate.now().getYear();

			if (income.getEmpIncome() <= 0 && graduationYear < currentYear) {
				response.setPlanStatus("AP");
			} else {
				response.setPlanStatus("DN");
				response.setDenialReason("Rules Not Matched");
			}
		}

		if (response.getPlanStatus().equalsIgnoreCase("AP")) {
			response.setPlanStartDate(LocalDate.now());
			response.setPlanEndDate(LocalDate.now().plusMonths(6));
			response.setBenefitAmt(350.00);
		}
		return response;

	}

}
