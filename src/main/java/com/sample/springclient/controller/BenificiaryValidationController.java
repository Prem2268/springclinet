package com.sample.springclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BenificiaryValidationController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/benificiaryvalidation")
	public String banificiaryValidation() {
		
		ResponseEntity<String> response = restTemplate.getForEntity("https://192.168.43.85:8005/beneficiary", String.class);
		return response.getBody();

	}

}
