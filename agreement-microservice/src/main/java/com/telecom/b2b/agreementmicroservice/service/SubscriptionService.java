package com.telecom.b2b.agreementmicroservice.service;

import com.telecom.b2b.agreementmicroservice.entity.Subscriptions;

import java.util.Optional;

public interface SubscriptionService {

    public Subscriptions saveSubscriptions(Subscriptions subscriptions);

    Optional<Subscriptions> findSubscriptionsById(Long subscriptionId);
}
