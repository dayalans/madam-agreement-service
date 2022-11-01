package com.telecom.b2b.agreementmicroservice.serviceimpl;

import com.telecom.b2b.agreementmicroservice.entity.PricePlans;
import com.telecom.b2b.agreementmicroservice.repository.PricePlanRepository;
import com.telecom.b2b.agreementmicroservice.service.PricePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PricePlanServiceImpl implements PricePlanService {

    @Autowired
    PricePlanRepository pricePlanRepository;
    @Override
    public PricePlans savePricePlan(PricePlans pricePlans) {
        return pricePlanRepository.save(pricePlans);
    }
}
