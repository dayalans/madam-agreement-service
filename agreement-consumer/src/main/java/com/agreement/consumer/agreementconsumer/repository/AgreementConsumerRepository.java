package com.agreement.consumer.agreementconsumer.repository;

import com.agreement.consumer.agreementconsumer.entity.Agreement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AgreementConsumerRepository extends MongoRepository<Agreement, Long> {
}
