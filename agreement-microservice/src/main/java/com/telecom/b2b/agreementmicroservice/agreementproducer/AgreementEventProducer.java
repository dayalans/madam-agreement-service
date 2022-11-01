package com.telecom.b2b.agreementmicroservice.agreementproducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.telecom.b2b.agreementmicroservice.entity.Agreement;
import com.telecom.b2b.agreementmicroservice.entity.AgreementEvent;
import com.telecom.b2b.agreementmicroservice.entity.AgreementEventType;
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

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class AgreementEventProducer {

    @Autowired
    KafkaTemplate<Long,String> kafkaTemplate;

    String topic = "agreement-events";
    @Autowired
    ObjectMapper objectMapper;

    public ListenableFuture<SendResult<Long,String>> sendAgreementEvent(Agreement agreement, AgreementEventType agreementEventType) throws JsonProcessingException {

        Long key = agreement.getAgreementId();
        AgreementEvent agreementEvent = new AgreementEvent();
        agreementEvent.setAgreement(agreement);
        agreementEvent.setAgreementEventType(agreementEventType);
        String value = objectMapper.writeValueAsString(agreementEvent);


        ProducerRecord<Long,String> producerRecord = buildProducerRecord(key, value, topic);

        ListenableFuture<SendResult<Long,String>> listenableFuture =  kafkaTemplate.send(producerRecord);

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

    private ProducerRecord<Long, String> buildProducerRecord(Long key, String value, String topic) {


        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "MADAM".getBytes()));

        return new ProducerRecord<Long,String>(topic,null,key,value,recordHeaders);
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
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().partition());
    }
}
