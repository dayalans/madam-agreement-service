package com.telecom.b2b.ucinstance.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.telecom.b2b.ucinstance.entity.UCCompany;
import com.telecom.b2b.ucinstance.service.UCCompanyService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UCCompanyController {

	@Autowired
	UCCompanyService ucCompanyService;

	@PostMapping("/v1/uccompany")
	public ResponseEntity<@Valid UCCompany> createUCInstance(@RequestBody @Valid UCCompany ucCompany) {

		try {
			ucCompany.setUcCompanyId("TCSE.SC." + ucCompany.getUcInstanceId());
			ucCompanyService.saveUCCompany(ucCompany);
			log.info("UCCompnay id saved with" + ucCompany.getUcCompanyId());

			return ResponseEntity.status(HttpStatus.CREATED).body(ucCompany);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/v1/uccompany/{ucCompanyId}")
	public ResponseEntity<UCCompany> updateUCInstance(@PathVariable("ucCompanyId") String ucCompanyId, @RequestBody UCCompany ucCompany) {

		try {
			
			ucCompanyService.updateUCCompany(ucCompanyId,ucCompany);
			log.info("UCCompnay id saved with" + ucCompany.getUcCompanyId());

			return ResponseEntity.status(HttpStatus.CREATED).body(ucCompany);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
