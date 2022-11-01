package com.agreement.consumer.agreementconsumer.controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.agreement.consumer.agreementconsumer.entity.AgreementEvent;
import com.agreement.consumer.agreementconsumer.entity.AgreementUCInstance;
import com.agreement.consumer.agreementconsumer.entity.UCInstance;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AgreementUCInstanceSummaryController {

	@Autowired
	MongoTemplate mongoTemplate;
	
	  @Autowired
	    ObjectMapper objectMapper;
	@GetMapping("/v1/agreementsummary/{agreementId}")
	public List<AgreementUCInstance> getAgreementUCInstance(@PathVariable(value = "agreementId") Long agreementId){
		
		    LookupOperation lookupOperation = LookupOperation.newLookup()
		                        .from("agreement")
		                        .localField("agreementId")
		                        .foreignField("agreementId")
		                        .as("agreement");
		    AggregationOperation unwind = Aggregation.unwind("agreement");
		    Aggregation aggregation = Aggregation.newAggregation(lookupOperation, unwind, Aggregation.match(Criteria.where("agreement.agreementId").is(agreementId)));//If I use this then it's working fine. 
		  List<AgreementUCInstance> results =  (List<AgreementUCInstance>) mongoTemplate.aggregate(aggregation, "ucinstance", AgreementUCInstance.class).getMappedResults();
		    System.out.println("Obj Size " +results.size());
		    System.out.println(" Results : "+results.toString());
		    
		return results;
	
		
	}
	
	


	@GetMapping("/v1/ucinstancesummary/{ucType}")
	public List<Document> getUCInstance(@PathVariable(value = "ucType") String ucType){
		

		AggregationOperation match = Aggregation.match(Criteria.where("ucType").is(ucType));
		AggregationOperation sort = Aggregation.sort(Direction.ASC, "_id");
		
		Aggregation aggregation = Aggregation.newAggregation(match, sort);
		
		
		List<Document> contacts =  mongoTemplate.aggregate(aggregation, "ucinstance", Document.class).getMappedResults();
		List<UCInstance> ucInstanceList=new ArrayList<UCInstance>();
		for (Document cur: contacts) {
			
			System.out.println(cur.toJson());
			
			try {
				UCInstance ucInstance = objectMapper.readValue(cur.toJson(), UCInstance.class);
				System.out.println(ucInstance.toString());
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    //System.out.println(cur)UCInstance
		}  
		return contacts;
	
		
	}
}
