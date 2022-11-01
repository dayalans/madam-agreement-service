package com.telecom.b2b.ucinstance.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telecom.b2b.ucinstance.entity.UCInstance;
import com.telecom.b2b.ucinstance.entity.UCUser;
import com.telecom.b2b.ucinstance.repository.UCInstanceRepository;
import com.telecom.b2b.ucinstance.service.UCInstanceService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UcInstanceController {

	@Autowired
	UCInstanceService ucInstanceService;
	
	@Autowired
	UCInstanceRepository ucInstanceRepository;

	@PostMapping("/v1/ucinstance")
	public ResponseEntity<UCInstance> createUCInstance(@RequestBody @Valid UCInstance ucInstance) {

		try {
			ucInstanceService.saveUCInstance(ucInstance);
			log.info("Instance id saved with" + ucInstance.getUcInstanceID());
			return ResponseEntity.status(HttpStatus.CREATED).body(ucInstance);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/v1/ucinstance/{ucInstanceId}")
	public ResponseEntity<UCInstance> updateUCInstance(@PathVariable("ucInstanceId") long ucInstanceId, @RequestBody @Valid UCInstance ucInstance) {

		try {
			ucInstanceService.updateUCInstance(ucInstanceId,ucInstance);
			log.info("Instance id saved with" + ucInstance.getUcInstanceID());
			return ResponseEntity.status(HttpStatus.CREATED).body(ucInstance);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@DeleteMapping("/v1/ucInstance/{ucInstanceId}")  
	public ResponseEntity<UCInstance> deleteUCUser(@PathVariable(value = "ucInstanceId") Long ucInstanceId)  
	{
	
	try {
		UCInstance ucInstance  = ucInstanceService.deleteUCInstance(ucInstanceId);
		log.info("Instance id deleted with" + ucInstanceId);
		return ResponseEntity.status(HttpStatus.OK).body(ucInstance);
	} catch (Exception e) {
		e.printStackTrace();
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
	@GetMapping("/v1/ucinstance/{ucinstanceId}")
	public ResponseEntity<UCInstance> getUcInstanceById(@PathVariable(value = "ucinstanceId") Long ucinstanceId) {

		try {
		Optional<UCInstance> ucInstance = ucInstanceRepository.findByucInstanceID(ucinstanceId);
		if (ucInstance.isPresent()) {
			return new ResponseEntity<>(ucInstance.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}} catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@SuppressWarnings("unused")
	private AccessTokenResponse getAccessTokenResponse(HttpEntity<MultiValueMap<String, String>> request, String url) {
		RestTemplate restTemplate = new RestTemplate();
	    try {
	    	
	        ResponseEntity<AccessTokenResponse> response = restTemplate.postForEntity(url, request, AccessTokenResponse.class);
	        return response.getBody();
	    } catch (ResourceAccessException e) {
	        log.error("KeyCloak getAccessTokenResponse: " + e.getMessage());
	        try {
	            ResponseEntity<AccessTokenResponse> response = restTemplate.postForEntity(url, request, AccessTokenResponse.class);
	            return response.getBody();
	        } catch (Exception ex) {
	            throw ex;
	        }
	    } catch (Exception e) {
	        throw e;
	    }
	}
	
	
	@GetMapping("/v1/ucinstance/{ucinstanceId}/validate/")
	public ResponseEntity<Map<String,String>> getUcInstanceDetails(@PathVariable(value = "ucinstanceId") Long ucinstanceId) {

		try {
		Optional<UCInstance> ucInstance = ucInstanceRepository.findByucInstanceID(ucinstanceId);
		Map<String,String> ucInstanceDetails= new HashMap<String,String>();
		ucInstanceDetails.put("UNINSTANCEID", String.valueOf(ucInstance.get().getUcInstanceID()));
		ucInstanceDetails.put("AGREEMENTID", String.valueOf(ucInstance.get().getAgreementId()));
		return new ResponseEntity<>(ucInstanceDetails, HttpStatus.OK);
	}catch (Exception ex) {
        throw ex;
    }
}
	
	

	@PostMapping("/v1/ucinstance/test")
	public void getUcInstanceDetailsByIDs(@RequestBody String payload) throws Exception {

		try {
			
			System.out.println(payload);
			
			ObjectMapper objectMapper = new ObjectMapper();

			//convert json file to map
			Map<?, ?> map = objectMapper.readValue(payload, Map.class);

			//iterate over map entries and print to console
			for (Map.Entry<?, ?> entry : map.entrySet()) {
			    System.out.println(entry.getKey() + "=" + entry.getValue());
			}
			
			UCInstance ucInstance = ucInstanceRepository.findByAgreementIDAndCustomerID( Long.parseLong(map.get("AGREEMENTID").toString()),Long.parseLong(map.get("CUSTOMERID").toString()));
			System.out.println(ucInstance.getUcInstanceID());
	}catch (Exception ex) {
        throw ex;
    }
}
}
