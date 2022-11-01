package com.telecom.b2b.ucinstance.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.b2b.ucinstance.entity.UCInstance;
import com.telecom.b2b.ucinstance.entity.UCUser;
import com.telecom.b2b.ucinstance.kafkaproducer.KafkaEventProducer;
import com.telecom.b2b.ucinstance.repository.UCInstanceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UCInstanceService {

	@Autowired
	UCInstanceRepository ucInstanceRepository;
	@Autowired
	KafkaEventProducer kafkaEventProducer;

	public UCInstance saveUCInstance(UCInstance ucInstance) {
		ucInstance= ucInstanceRepository.save(ucInstance);
		 kafkaEventProducer.sendUCInstanceSummaryEvent("UCINSTANCE_CREATE",ucInstance.getUcInstanceID(), ucInstance);
		return ucInstance;

	}
	
	
	public UCInstance updateUCInstance(Long ucInstanceId, UCInstance ucInstanceNew) {
		UCInstance ucInstanceOld= ucInstanceRepository.findById(ucInstanceId).get();
		ucInstanceOld.setAgreementId(ucInstanceNew.getAgreementId());
		ucInstanceOld.setCustomerId(ucInstanceNew.getCustomerId());
		ucInstanceOld.setUcOfferCode(ucInstanceNew.getUcOfferCode());
		ucInstanceOld.setUcType(ucInstanceNew.getUcType());
		ucInstanceOld.setUcVersion(ucInstanceNew.getUcVersion());
				
		ucInstanceRepository.save(ucInstanceOld);
		 kafkaEventProducer.sendUCInstanceSummaryEvent("UCINSTANCE_UPDATE",ucInstanceOld.getUcInstanceID(), ucInstanceOld);
		return ucInstanceOld;

	}
	
	public UCInstance getUCInstanceById(Long ucInstanceId) {
		return ucInstanceRepository.findById(ucInstanceId).get();
	}


	public UCInstance deleteUCInstance(Long ucInstanceId) {
		Optional<UCInstance> ucInstance = ucInstanceRepository.findById(ucInstanceId);
			if(ucInstance.isPresent()) {
				 ucInstanceRepository.deleteById(ucInstanceId);
				 kafkaEventProducer.sendUCInstanceSummaryEvent("UCINSTANCE_DELETE",ucInstanceId, ucInstance.get());
				 return ucInstance.get();
			}
		
			else {
				log.info("UCINSTANCE NOT FOUND");
			}
			return null;
	}
}
