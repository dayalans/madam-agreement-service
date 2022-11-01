package com.agreement.consumer.agreementconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface AgreementEventsConsumerService {
    void processAgreementEvents(ConsumerRecord<Long, String> consumerRecord) throws JsonProcessingException;
    void processUCEvents(ConsumerRecord<Long, String> consumerRecord) throws JsonProcessingException;
}
