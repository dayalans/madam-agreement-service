package com.tele.orderengine.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tele.orderengine.OrderEngineMapRepository;
import com.tele.orderengine.entity.OrderMap;

@Service
public class OrderengineService {

	@Autowired
	OrderEngineMapRepository orderEngineMapRepository;

	@Autowired
	ObjectMapper objectMapper;

	public Long saveOrderMap(ConsumerRecord<Long, String> consumerRecord) {

		OrderMap orderMap = new OrderMap();
		try {
			orderMap = objectMapper.readValue(consumerRecord.value(), OrderMap.class);
			orderMap.setOrderUCInstanceID(consumerRecord.key());
			orderEngineMapRepository.save(orderMap);
			System.out.println(orderMap.getOrderDetails());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return orderMap.getOrderMapId();

	}

}
