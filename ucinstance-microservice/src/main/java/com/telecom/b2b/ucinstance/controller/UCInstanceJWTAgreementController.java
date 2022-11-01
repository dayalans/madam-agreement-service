package com.telecom.b2b.ucinstance.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.telecom.b2b.ucinstance.entity.UCInstance;
import com.telecom.b2b.ucinstance.repository.UCInstanceRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UCInstanceJWTAgreementController {

	@Autowired
	UCInstanceRepository ucInstanceRepository;

	@PostMapping("/v1/JWT/ucinstance")
	public ResponseEntity<UCInstance> createUCInstance(@RequestBody @Valid UCInstance ucInstance) {

		try {
			
			
			MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
			requestParams.add("client_id", "easybank-callcentre");
			requestParams.add("grant_type", "client_credentials");
			requestParams.add("client_secret","BbIfZA1Xl1CMhafIZOI3rpBIBYsHeeIy");
			requestParams.add("scope", "openid");

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestParams, headers);

			String url = "http://localhost:7080/realms/master/protocol/openid-connect/token";

			AccessTokenResponse keycloakAccessToken = getAccessTokenResponse(request, url);
			System.out.println("token"+keycloakAccessToken.getToken());
			
			String authToken = keycloakAccessToken.getToken();

		    HttpHeaders headersNew = new HttpHeaders();
		    headersNew.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		    headersNew.add("Authorization", "Bearer "+authToken );
		    RestTemplate restTemplate = new RestTemplate();
		    ResponseEntity<String> response = restTemplate.exchange(
		            "http://localhost:8000/agreement-service/v1/agreement/"+ucInstance.getAgreementId(),
		            HttpMethod.GET,
		            new HttpEntity<>("parameters", headersNew),
		            String.class
		    );

		    System.out.println(response.getBody());
			ucInstanceRepository.save(ucInstance);
			log.info("Instance id saved with" + ucInstance.getUcInstanceID());
			return ResponseEntity.status(HttpStatus.CREATED).body(ucInstance);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/v1/ucinstance/JWT/{ucinstanceId}")
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
	
	
	@GetMapping("/v1/ucinstance/JWT/{ucinstanceId}/validate/")
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
}
