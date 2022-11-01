package com.telecom.b2b.agreementmicroservice.repository;

import com.telecom.b2b.agreementmicroservice.entity.Agreement;
import com.telecom.b2b.agreementmicroservice.entity.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionsRepository extends JpaRepository<Subscriptions,Long> {
}
