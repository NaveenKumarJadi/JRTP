package com.naveen.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naveen.binding.Child;
import com.naveen.binding.ChildRequest;
import com.naveen.binding.DcSummary;
import com.naveen.binding.Education;
import com.naveen.binding.Income;
import com.naveen.binding.PlanSelection;
import com.naveen.entity.CitizenAppEntity;
import com.naveen.entity.DcCaseEntity;
import com.naveen.entity.DcChildrenEntity;
import com.naveen.entity.DcEducationEntity;
import com.naveen.entity.DcIncomeEntity;
import com.naveen.entity.PlanEntity;
import com.naveen.repository.CitizenAppRepository;
import com.naveen.repository.DcCaseRepo;
import com.naveen.repository.DcChildrenRepo;
import com.naveen.repository.DcEducationRepo;
import com.naveen.repository.DcIncomeRepo;
import com.naveen.repository.PlanRepository;

@Service
public class DcServiceImpl implements DcService {

	@Autowired
	private DcCaseRepo dcCaseRepo;

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private DcIncomeRepo dcIncomeRepo;

	@Autowired
	private DcEducationRepo eduRepo;

	@Autowired
	private DcChildrenRepo childRepo;

	@Autowired
	private CitizenAppRepository appRepo;

	@Override
	public Long loadCaseNum(Integer appId) {

		Optional<CitizenAppEntity> app = appRepo.findById(appId);

		if (app.isPresent()) {

			DcCaseEntity entity = new DcCaseEntity();
			entity.setAppId(appId);

			entity = dcCaseRepo.save(entity);

			return entity.getCaseNum();
		}

		return (long) 01;
	}

	@Override
	public Map<Integer, String> getPlanNames() {

		List<PlanEntity> findAll = planRepository.findAll();

		Map<Integer, String> plansMap = new HashMap<>();

		for (PlanEntity entity : findAll) {
			plansMap.put(entity.getPlanId(), entity.getPlanName());
		}
		return plansMap;
	}

	@Override
	public Long savePlanSelection(PlanSelection planSelection) {

		Optional<DcCaseEntity> findById = dcCaseRepo.findById(planSelection.getCaseNum());
		if (findById.isPresent()) {
			DcCaseEntity dcCaseEntity = findById.get();
			dcCaseEntity.setPlanId(planSelection.getPlanId());
			dcCaseRepo.save(dcCaseEntity);

			return planSelection.getCaseNum();
		}

		return null;
	}

	@Override
	public Long saveIncomeData(Income income) {

		DcIncomeEntity entity = new DcIncomeEntity();

		BeanUtils.copyProperties(income, entity);
		dcIncomeRepo.save(entity);

		return income.getCaseNum();
	}

	@Override
	public Long saveEducation(Education education) {

		DcEducationEntity entity = new DcEducationEntity();

		BeanUtils.copyProperties(education, entity);

		eduRepo.save(entity);

		return education.getCaseNum();
	}

	@Override
	public Long saveChildrens(ChildRequest request) {

		List<Child> childs = request.getChilds();
		Long caseNum = request.getCaseNum();
		
		for (Child c : childs) {
			DcChildrenEntity entity = new DcChildrenEntity();
			BeanUtils.copyProperties(c, entity);
			entity.setCaseNum(caseNum);
			childRepo.save(entity);

		}
//		childRepo.saveAll(entites);

		return request.getCaseNum();
	}

	@Override
	public DcSummary getSummary(Long caseNumber) {

		String planName = "";

		DcIncomeEntity incomeEntity = dcIncomeRepo.findByCaseNum(caseNumber);
		DcEducationEntity educationEntity = eduRepo.findByCaseNum(caseNumber);
		List<DcChildrenEntity> childsEntity = childRepo.findByCaseNum(caseNumber);

		Optional<DcCaseEntity> dcCase = dcCaseRepo.findById(caseNumber);
		if (dcCase.isPresent()) {
			Integer planId = dcCase.get().getPlanId();
			Optional<PlanEntity> plan = planRepository.findById(planId);
			if (plan.isPresent()) {
				planName = plan.get().getPlanName();
			}

		}

		// set the data to summary object

		DcSummary summary = new DcSummary();
		summary.setPlanName(planName);

		Income income = new Income();
		BeanUtils.copyProperties(incomeEntity, income);
		summary.setIncome(income);

		Education edu = new Education();
		BeanUtils.copyProperties(educationEntity, edu);
		summary.setEducation(edu);

		List<Child> childs = new ArrayList<>();
		for (DcChildrenEntity entity : childsEntity) {
			Child ch = new Child();
			BeanUtils.copyProperties(entity, ch);
			childs.add(ch);
		}
		summary.setChilds(childs);

		return summary;
	}

}
