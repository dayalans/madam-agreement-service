package com.telecom.b2b.agreementmicroservice.controller;

import com.telecom.b2b.agreementmicroservice.entity.*;
import com.telecom.b2b.agreementmicroservice.exception.ResourceNotFoundException;
import com.telecom.b2b.agreementmicroservice.service.AgreementService;
import com.telecom.b2b.agreementmicroservice.service.PricePlanService;
import com.telecom.b2b.agreementmicroservice.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j

public class AgreementController {
    @Autowired
    AgreementService agreementService;
    @Autowired
    SubscriptionService subscriptionService;
    @Autowired
    PricePlanService pricePlanService;

    @PostMapping("/v1/agreement")
    public ResponseEntity<Agreement> createAgreement(@RequestBody @Valid AgreementRequest agreementRequest)
    {
        try{

            Agreement agreement=agreementService.saveAgreement(agreementRequest);
            return new ResponseEntity<>(agreement, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/v1/agreement/new")
    public ResponseEntity<Agreement> createAgreementNew(@RequestBody @Valid Agreement agreement) throws URISyntaxException, IOException
    {
        
        		System.out.println("code inside createAgreementNew ");
            Agreement agreementnew=agreementService.saveAgreementNew(agreement);
            return new ResponseEntity<>(agreementnew, HttpStatus.CREATED);

        
    }

    @PostMapping("/v1/agreement/{agreementId}/subscriptions")
    public Subscriptions createSubscription(@PathVariable (value = "agreementId") Long agreementId,
                                 @Valid @RequestBody Subscriptions subscriptions) {
        return agreementService.getAgreementById(agreementId).map(existingagreement -> {
            subscriptions.setAgreement(existingagreement);
            return subscriptionService.saveSubscriptions(subscriptions);
        }).orElseThrow(() -> new ResourceNotFoundException("AgreementId " + agreementId + " not found"));
    }

    @PostMapping("/v1/agreement/{subscriptionId}/pricePlans")
    public PricePlans savepricePlan(@PathVariable (value = "subscriptionId") Long subscriptionId,
                                       @Valid @RequestBody PricePlans pricePlans) {
        return subscriptionService.findSubscriptionsById(subscriptionId).map(existingSubscription -> {
            pricePlans.setSubscription(existingSubscription);
            return pricePlanService.savePricePlan(pricePlans);
        }).orElseThrow(() -> new ResourceNotFoundException("AgreementId " + subscriptionId + " not found"));
    }


    @PutMapping("/v1/agreement/{agreementId}")
    public Agreement updateAgreement(@PathVariable Long agreementId, @Valid @RequestBody Agreement agreement) {
        try{


        return agreementService.getAgreementById(agreementId).map(existingAgreement -> {
            existingAgreement.setAgreementType(agreement.getAgreementType());
            existingAgreement.setAgreementStatus(agreement.getAgreementStatus());

            try {
                return agreementService.saveAgreementNew(agreement);
            } catch (Exception e) {
                throw (new RuntimeException("Exception occurred while save agreement with id"+agreementId));
            }

        }).orElseThrow(() -> new ResourceNotFoundException("agreementId " + agreementId + " not found"));
    }catch (Exception e) {
            e.printStackTrace();
            throw (new RuntimeException("Exception occurred while save agreement with id"+agreementId));
        }
    }
    @GetMapping("/v1/agreement/{agreementId}")
    public ResponseEntity<Agreement> getAgreementById(@RequestBody @Valid @PathVariable("agreementId") long agreementId)
    {
        Optional<Agreement> agreement = agreementService.getAgreementById(agreementId);
        if (agreement.isPresent()) {
            return new ResponseEntity<>(agreement.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/v1/agreement/customer/{agreementId}")
    public ResponseEntity<AgreementCustomer> getAgreementCustomerById(@RequestBody @Valid @PathVariable("agreementId") long agreementId) throws URISyntaxException {
        AgreementCustomer agreementCustomer = agreementService.getAgreementCustomerById(agreementId);
        if (agreementCustomer!=null) {
            return new ResponseEntity<>(agreementCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/v1/agreement/customer/{customerId}")
    public ResponseEntity<List<Agreement>> getAgreementsByCustomerId(@RequestBody @Valid @PathVariable("customerId") long customerId)
    {
        List<Agreement> agreementData = agreementService.getAgreementByCustomerId(customerId);
        if (!agreementData.isEmpty()) {
            return new ResponseEntity<>(agreementData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/v1/agreement/customerfeign/{agreementId}")
    public ResponseEntity<AgreementCustomer> getAgreementCustomerFeignById(@RequestBody @Valid @PathVariable("agreementId") long agreementId) throws URISyntaxException {

        log.info("code is here"+agreementId);
        AgreementCustomer agreementCustomer = agreementService.getAgreementCustomerByFeign(agreementId);
        if (agreementCustomer!=null) {
            return new ResponseEntity<>(agreementCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }



}
