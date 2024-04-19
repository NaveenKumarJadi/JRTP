package com.naveen.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.binding.ChildRequest;
import com.naveen.binding.DcSummary;
import com.naveen.service.DcService;

@RestController
public class ChildRestController {

	@Autowired
	private DcService dcService;

	@PostMapping("/childrens")
	public ResponseEntity<DcSummary> saveChilds(@RequestBody ChildRequest request) {

		Long caseNum = dcService.saveChildrens(request);

		DcSummary summary = dcService.getSummary(caseNum);

		return new ResponseEntity<DcSummary>(summary, HttpStatus.OK);

	}

}
