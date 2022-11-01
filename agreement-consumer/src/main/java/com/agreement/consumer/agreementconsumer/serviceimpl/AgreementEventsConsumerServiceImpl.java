package com.agreement.consumer.agreementconsumer.serviceimpl;

import java.util.Date;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.agreement.consumer.agreementconsumer.entity.Agreement;
import com.agreement.consumer.agreementconsumer.entity.AgreementEvent;
import com.agreement.consumer.agreementconsumer.entity.UCInstance;
import com.agreement.consumer.agreementconsumer.entity.UCInstanceEvent;
import com.agreement.consumer.agreementconsumer.entity.UCUser;
import com.agreement.consumer.agreementconsumer.repository.AgreementConsumerRepository;
import com.agreement.consumer.agreementconsumer.service.AgreementEventsConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.client.result.UpdateResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AgreementEventsConsumerServiceImpl implements AgreementEventsConsumerService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private AgreementConsumerRepository agreementConsumerRepository;

    @Autowired
    MongoTemplate mongoTemplate;
    @Override
    public void processAgreementEvents(ConsumerRecord<Long, String> consumerRecord) throws JsonProcessingException {
        AgreementEvent agreementEvent = objectMapper.readValue(consumerRecord.value(), AgreementEvent.class);
        log.info("agreementEvent : {} ", agreementEvent);
        log.info("agreementEvent : {} ", agreementEvent.getAgreementEventType());
        switch(agreementEvent.getAgreementEventType()){
            case CREATE:
                saveAgreement(agreementEvent.getAgreement());
                break;
            case UPDATE:
                UpdateAgreement(agreementEvent.getAgreement());
                break;
            case DELETE:
                deleteAgreement(agreementEvent.getAgreement());
                break;
            default:
                log.info("Invalid Library Event Type");
        }

                saveAgreement(agreementEvent.getAgreement());


    }


    private void saveAgreement(Agreement agreement) {

        mongoTemplate.save(agreement);
        log.info("Successfully Persisted the agreementEvent {} ", agreement);
    }

    private void deleteAgreement(Agreement  agreementEvent) {
        mongoTemplate.remove(agreementEvent);
        log.info("Successfully Persisted the agreementEvent {} ", agreementEvent);
    }

    private void UpdateAgreement(Agreement  agreementEvent) {
        //Implement this later in the future
       // mongoTemplate.findAndModify();
        log.info("Successfully Persisted the agreementEvent {} ", agreementEvent);
    }
    
   


	@Override
	public void processUCEvents(ConsumerRecord<Long, String> consumerRecord) throws JsonProcessingException {

		UCInstanceEvent ucInstanceEvent = objectMapper.readValue(consumerRecord.value(), UCInstanceEvent.class);
		log.info("agreementEvent : {} ", ucInstanceEvent);
		log.info("agreementEvent : {} ", ucInstanceEvent.getUcInstanceEventType());
		UCInstance ucInstance = new UCInstance();
		if (ucInstanceEvent.getUcInstanceEventType().equalsIgnoreCase("UCINSTANCE_CREATE")) {

			ucInstance.setAgreementId(ucInstanceEvent.getUcInstance().getAgreementId());
			ucInstance.setCustomerId(ucInstanceEvent.getUcInstance().getCustomerId());
			ucInstance.setUcEndDate(new Date());
			ucInstance.setUcInstanceID(ucInstanceEvent.getUcInstance().getUcInstanceID());
			ucInstance.setUcOfferCode(ucInstanceEvent.getUcInstance().getUcOfferCode());
			ucInstance.setUcType(ucInstanceEvent.getUcInstance().getUcType());
			ucInstance.setUcVersion(ucInstanceEvent.getUcInstance().getUcVersion());
			mongoTemplate.save(ucInstance);
		}
		if (ucInstanceEvent.getUcInstanceEventType().equalsIgnoreCase("UCCOMPANY_CREATE")) {
			if (ucInstanceEvent.getUcCompany() != null) {

				Query query = new Query();
				query.addCriteria(Criteria.where("_id").is(ucInstanceEvent.getUcCompany().getUcInstanceId()));
				Update update = new Update();
				//update.push("ucInstance", ucInstanceEvent.getUcCompany());
				update.addToSet("ucCompany", ucInstanceEvent.getUcCompany());
				mongoTemplate.updateFirst(query, update, "ucinstance");

			}

		}
		
		if (ucInstanceEvent.getUcInstanceEventType().equalsIgnoreCase("UCCOMPANY_UPDATE")) {
			if (ucInstanceEvent.getUcCompany() != null) {

				 Query query = new Query().addCriteria(Criteria.where("ucCompany.ucCompanyId").is(ucInstanceEvent.getUcCompany().getUcCompanyId()));
			        Update updateDefinition = new Update();
			        updateDefinition.set("ucCompany", ucInstanceEvent.getUcCompany());
			        mongoTemplate.updateFirst(query, updateDefinition, "ucinstance");
			        log.info("Successfully Persisted the agreementEvent {} ", ucInstanceEvent.getUcCompany().getUcCompanyId());

			}

		}
		
		if (ucInstanceEvent.getUcInstanceEventType().equalsIgnoreCase("UCINSTANCE_UPDATE")) {
			if (ucInstanceEvent.getUcInstance() != null) {

				 Query query = new Query().addCriteria(Criteria.where("_id").is(consumerRecord.key()));
			        Update updateDefinition = new Update();
			        updateDefinition.set("ucType", ucInstanceEvent.getUcInstance().getUcType());
			        updateDefinition.set("ucOfferCode", ucInstanceEvent.getUcInstance().getUcOfferCode());
			        updateDefinition.set("ucVersion", ucInstanceEvent.getUcInstance().getUcVersion());
			        updateDefinition.set("agreementId", ucInstanceEvent.getUcInstance().getAgreementId());
			        updateDefinition.set("customerId", ucInstanceEvent.getUcInstance().getCustomerId());
			        mongoTemplate.updateFirst(query, updateDefinition, "ucinstance");
			        log.info("Successfully Persisted the agreementEvent {} ", ucInstanceEvent.getUcInstance().getUcInstanceID());

			}

		}
		
		if (ucInstanceEvent.getUcInstanceEventType().equalsIgnoreCase("UCINSTANCE_DELETE")) {
			if (ucInstanceEvent.getUcInstance() != null) {

				 Query query = new Query().addCriteria(Criteria.where("_id").is(consumerRecord.key()));
			        mongoTemplate.findAndRemove(query, UCInstance.class);
			        log.info("Successfully deleted the ucInstance {} ", ucInstanceEvent.getUcInstance().getUcInstanceID());

			}

		}
		
		if (ucInstanceEvent.getUcInstanceEventType().equalsIgnoreCase("UCUSER_CREATE")) {
			if (ucInstanceEvent.getUcUser() != null) {
				Query query = new Query();
				query.addCriteria(Criteria.where("_id").is(consumerRecord.key()));
				Update update = new Update();
				update.addToSet("ucUser", ucInstanceEvent.getUcUser());
				mongoTemplate.updateFirst(query, update, "ucinstance");

			}

		log.info("Successfully Persisted the ucInstancetEvent {} ", ucInstance);

	}
		
	if (ucInstanceEvent.getUcInstanceEventType().equalsIgnoreCase("UCUSER_DELETE")) {
		if (ucInstanceEvent.getUcUser() != null) {
			Query queryUser = Query.query(Criteria.where("_id").is(consumerRecord.key()));
			Query queryVideo = Query.query(Criteria.where("ucUserId").is(ucInstanceEvent.getUcUser().getUcUserId()));
			Update update = new Update().pull("ucUser", queryVideo);
			mongoTemplate.updateFirst(queryUser, update, "ucinstance");

			log.info("Successfully Deleted the user {} ", ucInstanceEvent.getUcUser().getUcUserId());
		}

	}
	
	
	if (ucInstanceEvent.getUcInstanceEventType().equalsIgnoreCase("UCUSER_UPDATE")) {
		if (ucInstanceEvent.getUcUser() != null) {
			 Query query = Query.query(Criteria.where("_id").is(consumerRecord.key()).and("ucUser.ucUserId").is(ucInstanceEvent.getUcUser().getUcUserId()));
			 Update update = new Update().set("ucUser.$", ucInstanceEvent.getUcUser());
			 mongoTemplate.updateFirst(query, update, "ucinstance");

		}

	}
	}
}
