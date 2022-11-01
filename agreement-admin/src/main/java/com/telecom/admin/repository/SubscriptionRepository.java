package com.telecom.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telecom.admin.entity.Subscription;


public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
	
	public Optional<Subscription> findBysubscriptionCode(String subscriptionCode);

}
