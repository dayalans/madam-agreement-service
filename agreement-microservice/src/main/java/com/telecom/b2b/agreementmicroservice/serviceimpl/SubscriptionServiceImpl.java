package com.telecom.b2b.agreementmicroservice.serviceimpl;

import com.telecom.b2b.agreementmicroservice.entity.Subscriptions;
import com.telecom.b2b.agreementmicroservice.repository.SubscriptionsRepository;
import com.telecom.b2b.agreementmicroservice.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    SubscriptionsRepository subscriptionsRepository;

    @Override
    public Subscriptions saveSubscriptions(Subscriptions subscriptions) {
       return subscriptionsRepository.save(subscriptions);
    }
    @Override
    public Optional<Subscriptions> findSubscriptionsById(Long subscriptionId) {
        return subscriptionsRepository.findById(subscriptionId);
    }


}
