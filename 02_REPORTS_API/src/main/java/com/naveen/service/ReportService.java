package com.naveen.service;

import java.util.List;

import com.naveen.request.SearchRequest;
import com.naveen.response.SearchResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {

	public List<String> getUniquePlanNames();

	public List<String> getUniquePlanStatus();

	public List<SearchResponse> search(SearchRequest request);

	public void generateExcel(HttpServletResponse response) throws Exception;
//	public HttpServletResponse generatedExcel();
	
	public void generatePdf(HttpServletResponse response) throws Exception;

}
