package com.telecom.b2b.ucinstance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.b2b.ucinstance.entity.UCCompany;
import com.telecom.b2b.ucinstance.kafkaproducer.KafkaEventProducer;
import com.telecom.b2b.ucinstance.repository.UCCompanyRepository;

@Service
public class UCCompanyService {
	@Autowired
	UCCompanyRepository ucCompanyRepository;
	@Autowired
	KafkaEventProducer kafkaEventProducer;
	
	public UCCompany saveUCCompany(UCCompany ucCompany) {
		ucCompany= ucCompanyRepository.save(ucCompany);
		kafkaEventProducer.sendUCCompanySummaryEvent(ucCompany.getUcInstanceId(), ucCompany, "UCCOMPANY_CREATE");
		return ucCompany;
	}
	
	
	public UCCompany updateUCCompany(String ucCompanyId, UCCompany ucCompany) {
		UCCompany oldCompany=ucCompanyRepository.findById(ucCompanyId).get();
		oldCompany.setUcBillingNumber(ucCompany.getUcBillingNumber());
		oldCompany.setUcCompanyName(ucCompany.getUcCompanyName());
		ucCompany= ucCompanyRepository.save(oldCompany);
		kafkaEventProducer.sendUCCompanySummaryEvent(ucCompany.getUcInstanceId(), ucCompany, "UCCOMPANY_UPDATE");
		return ucCompany;
	}
	
	public UCCompany getUCCompanyByID(String ucCompanyId) {
		return ucCompanyRepository.findById(ucCompanyId).get();
		
	}
	
}
