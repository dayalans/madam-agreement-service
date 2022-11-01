package com.telecom.admin.controller;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.telecom.admin.entity.Subscription;
import com.telecom.admin.service.AgreementAdminService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AgreementAdminController {
	
	@Autowired
	AgreementAdminService agreementAdminService;
	
	
	
	
	@PostMapping("/v1/agreementadmin/subscription")
	public ResponseEntity<Subscription> createSubscription(@Valid @RequestBody Subscription subscription) {

		try {
			log.info("AgreementAdminController: createSubscription"+subscription.getSubscriptionCode());
			agreementAdminService.saveOrUpdate(subscription);
			return ResponseEntity.status(HttpStatus.CREATED).body(subscription);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

	@PutMapping("/v1/agreementadmin/subscription")
	public ResponseEntity<Subscription> updateSubscription(@Valid @RequestBody Subscription subscription) {

		try {
			log.info("AgreementAdminController: createSubscription"+subscription.getSubscriptionCode());
			agreementAdminService.saveOrUpdate(subscription);
			return ResponseEntity.status(HttpStatus.CREATED).body(subscription);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/v1/agreementadmin/subscription/{subscriptionCode}")
	public ResponseEntity<Subscription> getSubscriptionByCode(@PathVariable(value = "subscriptionCode") String subscriptionCode) {

		try {
		Optional<Subscription> subscription = agreementAdminService.findBysubscriptionCode(subscriptionCode);
		if (subscription.isPresent()) {
			return new ResponseEntity<>(subscription.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}} catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("/v1/agreementadmin/subscription")
	public ResponseEntity<List<Subscription>> getUcInstanceById() {

		try {
			List<Subscription> subscriptions = agreementAdminService.findAll();
		
			return ResponseEntity.status(HttpStatus.CREATED).body(subscriptions);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}

}
