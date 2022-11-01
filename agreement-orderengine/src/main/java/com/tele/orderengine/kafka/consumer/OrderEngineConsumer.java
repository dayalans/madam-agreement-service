package com.tele.orderengine.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tele.orderengine.service.OrderengineService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderEngineConsumer {

	@Autowired
	private OrderengineService orderengineService;

	@KafkaListener(topics = { "orderengine-initiate-events" })
	public Long onMessage(ConsumerRecord<Long, String> consumerRecord) throws JsonProcessingException {

		log.info("ConsumerRecord : {} ", consumerRecord);
		return orderengineService.saveOrderMap(consumerRecord);

	}
}
