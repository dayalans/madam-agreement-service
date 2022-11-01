package com.telecom.b2b.ucinstance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.b2b.ucinstance.entity.UCUser;
import com.telecom.b2b.ucinstance.entity.UCUserFeature;
import com.telecom.b2b.ucinstance.kafkaproducer.KafkaEventProducer;
import com.telecom.b2b.ucinstance.repository.UCUserRepository;

@Service
public class UCUserService {
	

	@Autowired
	UCUserRepository uCUserRepository;
	@Autowired
	KafkaEventProducer kafkaEventProducer;
	
	public UCUser saveUCUser(UCUser ucUser) {
		List<UCUserFeature> userFeatureList = ucUser.getUcUserFeature();
		for (UCUserFeature ucUserFeature : userFeatureList) {
			ucUserFeature.setUcUser(ucUser);
		}
		ucUser.setUcUserFeature(userFeatureList);
		uCUserRepository.save(ucUser);
		kafkaEventProducer.sendUCUserSummaryEvent(ucUser, "UCUSER_CREATE");
		return ucUser;
		
	}
	
	
	public UCUser deleteUCUser(Long ucUserId) {
		
		Optional<UCUser> ucUser=uCUserRepository.findById(ucUserId);
		if(ucUser.isPresent()) {
			uCUserRepository.deleteById(ucUserId);

			kafkaEventProducer.sendUCUserSummaryEvent(ucUser.get(), "UCUSER_DELETE");
			return ucUser.get();
		}
		return null;
		
		
		
	}
	
	public UCUser updateUCUser(UCUser ucUser) {
		List<UCUserFeature> userFeatureList = ucUser.getUcUserFeature();
		for (UCUserFeature ucUserFeature : userFeatureList) {
			ucUserFeature.setUcUser(ucUser);
		}
		ucUser.setUcUserFeature(userFeatureList);
		uCUserRepository.save(ucUser);
		kafkaEventProducer.sendUCUserSummaryEvent(ucUser, "UCUSER_UPDATE");
		return ucUser;
		
	}
	
	public Optional<UCUser> findUCUserById(Long ucUserId) {

		Optional<UCUser> ucUser = uCUserRepository.findById(ucUserId);
		if (ucUser.isPresent()) {

			return ucUser;
		}
		return null;

	}

}
