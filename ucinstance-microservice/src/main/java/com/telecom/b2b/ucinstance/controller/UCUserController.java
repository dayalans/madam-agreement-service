package com.telecom.b2b.ucinstance.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.telecom.b2b.ucinstance.entity.UCUser;
import com.telecom.b2b.ucinstance.service.UCUserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UCUserController {

	@Autowired
	UCUserService ucuserService;

	@PostMapping("/v1/ucuser")
	public ResponseEntity<@Valid UCUser> createUCuser(@RequestBody @Valid UCUser ucUser) {

		try {

			ucuserService.saveUCUser(ucUser);

			log.info("UCUser id saved with" + ucUser.getUcUserId());

			return ResponseEntity.status(HttpStatus.CREATED).body(ucUser);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@DeleteMapping("/v1/ucuser/{ucUserId}")  
	public UCUser deleteUCUser(@PathVariable(value = "ucUserId") Long ucUserId)  
	{  
	UCUser ucuser=  ucuserService.deleteUCUser(ucUserId);
	return ucuser;
	 
	}
	
	
	@PutMapping("/v1/ucuser/{ucUserId}")
	  public ResponseEntity<UCUser> updateTutorial(@PathVariable("ucUserId") long ucUserId, @RequestBody UCUser ucUser) {
	    Optional<UCUser> ucUserData = ucuserService.findUCUserById(ucUserId);
	    if (ucUserData.isPresent()) {
	    	UCUser ucUserUpdate = ucUserData.get();
	    	ucUserUpdate.setUcUserFixednr(ucUser.getUcUserFixednr());
	      return new ResponseEntity<>(ucuserService.updateUCUser(ucUserUpdate), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	
	
}
