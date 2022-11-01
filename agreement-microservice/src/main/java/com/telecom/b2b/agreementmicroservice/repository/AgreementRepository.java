package com.telecom.b2b.agreementmicroservice.repository;

import com.telecom.b2b.agreementmicroservice.entity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgreementRepository extends JpaRepository<Agreement,Long> {
    List<Agreement> findByCustomerId(Long customerId);


}
