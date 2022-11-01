package com.telecom.b2b.ucinstance.kafkaproducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telecom.b2b.ucinstance.entity.OrderMap;
import com.telecom.b2b.ucinstance.entity.UCCompany;
import com.telecom.b2b.ucinstance.entity.UCInstance;
import com.telecom.b2b.ucinstance.entity.UCInstanceEvent;
import com.telecom.b2b.ucinstance.entity.UCUser;
import com.telecom.b2b.ucinstance.repository.UCCompanyRepository;
import com.telecom.b2b.ucinstance.repository.UCInstanceRepository;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

@Component
@Slf4j
public class KafkaEventProducer {

	@Autowired
	KafkaTemplate<Long, String> kafkaTemplate;

	String topic = "orderengine-initiate-events";
	
	String UcInstancetopic = "ucinstance-company";
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	UCInstanceRepository ucInstanceRepository;
	@Autowired
	UCCompanyRepository ucCompanyRepository;

	public ListenableFuture<SendResult<Long, String>> sendOrderEngineIntitiateEvent(Long ucInstanceId,UCCompany uccompany)
			throws JsonProcessingException {
		String ucCompanyJson = objectMapper.writeValueAsString(uccompany);
		Long key = ucInstanceId;
		OrderMap orderMap = new OrderMap();
		orderMap.setOrdermapAction("CREATE");
		orderMap.setOrderSystem("UCINSTANCE");
		orderMap.setOrderDetails(ucCompanyJson);
		orderMap.setOrderMapName("UCCOMPANY");
		orderMap.setOrdermapStatus("STARTED");

		String value = objectMapper.writeValueAsString(orderMap);

		ProducerRecord<Long, String> producerRecord = buildProducerRecord(key, value, topic);

		ListenableFuture<SendResult<Long, String>> listenableFuture = kafkaTemplate.send(producerRecord);

		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Long, String>>() {
			@Override
			public void onFailure(Throwable ex) {
				handleFailure(key, value, ex);
			}

			@Override
			public void onSuccess(SendResult<Long, String> result) {
				handleSuccess(key, value, result);
			}
		});
		return listenableFuture;
	}
	
	
	
	public ListenableFuture<SendResult<Long, String>> sendUCInstanceSummaryEvent(String eventType,Long ucInstanceId,UCInstance ucInstance)
	{
		ListenableFuture<SendResult<Long, String>> listenableFuture = null;
		Long key = ucInstanceId;
		String value;
		try {
			UCInstanceEvent ucInstanceEvent = new UCInstanceEvent();
			ucInstanceEvent.setUcInstanceEventType(eventType);
			ucInstanceEvent.setUcInstance(ucInstance);
			value = objectMapper.writeValueAsString(ucInstanceEvent);

			ProducerRecord<Long, String> producerRecord = buildProducerRecord(ucInstanceId, value, UcInstancetopic);

			listenableFuture = kafkaTemplate.send(producerRecord);

			listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Long, String>>() {
				@Override
				public void onFailure(Throwable ex) {
					handleFailure(key, value, ex);
				}

				@Override
				public void onSuccess(SendResult<Long, String> result) {
					handleSuccess(key, value, result);
				}
			});

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listenableFuture;
	}

	public ListenableFuture<SendResult<Long, String>> sendUCCompanySummaryEvent(Long ucInstanceId, UCCompany uccompany,
			String eventType) {
		ListenableFuture<SendResult<Long, String>> listenableFuture = null;
		try {
			Long key = ucInstanceId;
			String value;
			UCInstanceEvent ucInstanceEvent = new UCInstanceEvent();
			ucInstanceEvent.setUcCompany(uccompany);
			ucInstanceEvent.setUcInstanceEventType(eventType);
			value = objectMapper.writeValueAsString(ucInstanceEvent);

			ProducerRecord<Long, String> producerRecord = buildProducerRecord(ucInstanceId, value, UcInstancetopic);

			listenableFuture = kafkaTemplate.send(producerRecord);

			listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Long, String>>() {
				@Override
				public void onFailure(Throwable ex) {
					handleFailure(key, value, ex);
				}

				@Override
				public void onSuccess(SendResult<Long, String> result) {
					handleSuccess(key, value, result);
				}
			});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listenableFuture;
	}
	
	
	public ListenableFuture<SendResult<Long, String>> sendUCUserSummaryEvent(UCUser ucuser, String eventType) {
		ListenableFuture<SendResult<Long, String>> listenableFuture = null;
		try {

			UCInstanceEvent ucInstanceEvent = new UCInstanceEvent();
			Long ucInstanceId = Long.parseLong(ucuser.getUcCompanyId().substring(8));
			ucInstanceEvent.setUcUser(ucuser);
			ucInstanceEvent.setUcInstanceEventType(eventType);
			Long key = ucInstanceId;
			String value = objectMapper.writeValueAsString(ucInstanceEvent);

			ProducerRecord<Long, String> producerRecord = buildProducerRecord(ucInstanceId, value, UcInstancetopic);

			listenableFuture = kafkaTemplate.send(producerRecord);

			listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Long, String>>() {
				@Override
				public void onFailure(Throwable ex) {
					handleFailure(key, value, ex);
				}

				@Override
				public void onSuccess(SendResult<Long, String> result) {
					handleSuccess(key, value, result);
				}
			});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listenableFuture;
	}

	private ProducerRecord<Long, String> buildProducerRecord(Long key, String value, String topic) {

		List<Header> recordHeaders = List.of(new RecordHeader("event-source", "MADAM".getBytes()));

		return new ProducerRecord<Long, String>(topic, null, key, value, recordHeaders);
	}

	private void handleFailure(Long key, String value, Throwable ex) {
		log.error("Error Sending the Message and the exception is {}", ex.getMessage());
		try {
			throw ex;
		} catch (Throwable throwable) {
			log.error("Error in OnFailure: {}", throwable.getMessage());
		}

	}

	private void handleSuccess(Long key, String value, SendResult<Long, String> result) {
		log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value,
				result.getRecordMetadata().partition());
	}
}
