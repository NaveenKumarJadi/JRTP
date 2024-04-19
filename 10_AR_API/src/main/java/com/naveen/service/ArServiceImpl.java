package com.naveen.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientSsl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.naveen.binding.CitizenApp;
import com.naveen.entity.CitizenAppEntity;
import com.naveen.repository.CitizenAppRepository;

@Service
public class ArServiceImpl implements ArService {

	@Autowired
	private CitizenAppRepository appRepo;

	@Override
	public Integer createApplication(CitizenApp app) {

		// Make Rest call to ssa-web api with ssn as input
		String endPointUrl = "http://localhost:9099/ssn/{ssn}";
		
		/*
		//RestTemplate is deprecating so instead of this we are using webclient for this we need change the dependency for web to webflux
		RestTemplate rt = new RestTemplate();
		
		ResponseEntity<String> resEntity = rt.getForEntity(endPointUrl, String.class, app.getSsn());
		String stateName = resEntity.getBody();
		*/
		
		WebClient webClient = WebClient.create();
		
		String stateName = webClient.get() //it represent GET Request
				.uri(endPointUrl, app.getSsn()) // it represent  url to send req
				.retrieve() // to retreive response
				.bodyToMono(String.class) // to specigy response type
				.block(); // to make synchronus call
		
		if ("New Jersey".equals(stateName)) {
			// create application
			CitizenAppEntity entity = new CitizenAppEntity();
			BeanUtils.copyProperties(app, entity);

			entity.setStateName(stateName);
			CitizenAppEntity save = appRepo.save(entity);

			return save.getAppId();
		}
		return 0;
	}

}
