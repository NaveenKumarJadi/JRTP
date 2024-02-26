package com.naveen.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.request.SearchRequest;
import com.naveen.response.SearchResponse;
import com.naveen.service.ReportService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ReportRestController {

	@Autowired
	private ReportService reportService;

	@GetMapping("/plans")
	public ResponseEntity<List<String>> getPlanNames() {
		List<String> planNames = reportService.getUniquePlanNames();
		return new ResponseEntity<List<String>>(planNames, HttpStatus.OK);
	}

	@GetMapping("/status")
	public ResponseEntity<List<String>> getPlanStatus() {
		List<String> planStatus = reportService.getUniquePlanStatus();
		return new ResponseEntity<List<String>>(planStatus, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest request) {
		List<SearchResponse> response = reportService.search(request);
		return new ResponseEntity<List<SearchResponse>>(response, HttpStatus.OK);
	}

	@GetMapping("/excel")
	public void excelReport(HttpServletResponse response) throws Exception {

		response.setContentType("application/octet-stream");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=data.xls";

		response.setHeader(headerKey, headerValue);

		reportService.generateExcel(response);

	}

	@GetMapping("/pdf")
	public void pdfReport(HttpServletResponse response) throws Exception {

		response.setContentType("application/pdf");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=data.pdf";

		response.setHeader(headerKey, headerValue);

		reportService.generatePdf(response);
	}

}
