package com.telecom.b2b.ucinstance.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telecom.b2b.ucinstance.entity.UCInstance;
public interface UCInstanceRepository extends JpaRepository<UCInstance,Long> {
	Optional<UCInstance> findByucInstanceID(Long ucInstanceID);
	
	 @Query(value = "SELECT * FROM UCINSTANCE WHERE AGREEMENT_ID = ?1 AND CUSTOMER_ID = ?2", nativeQuery = true)
	  UCInstance findByAgreementIDAndCustomerID(Long agreementId,Long customerId);

}
