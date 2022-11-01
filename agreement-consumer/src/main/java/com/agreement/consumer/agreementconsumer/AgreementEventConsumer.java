package com.agreement.consumer.agreementconsumer;

import com.agreement.consumer.agreementconsumer.service.AgreementEventsConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AgreementEventConsumer {

    @Autowired
    private AgreementEventsConsumerService agreementEventsConsumerService;

    @KafkaListener(topics = {"agreement-events"})
    public void onMessage(ConsumerRecord<Long,String> consumerRecord) throws JsonProcessingException {

        log.info("ConsumerRecord : {} ", consumerRecord );
        agreementEventsConsumerService.processAgreementEvents(consumerRecord);

    }
}
