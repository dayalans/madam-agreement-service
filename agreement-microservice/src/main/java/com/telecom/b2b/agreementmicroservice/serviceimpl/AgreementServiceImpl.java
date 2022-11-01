package com.telecom.b2b.agreementmicroservice.serviceimpl;

import com.telecom.b2b.agreementmicroservice.agreementproducer.AgreementEventProducer;
import com.telecom.b2b.agreementmicroservice.entity.*;
import com.telecom.b2b.agreementmicroservice.exception.CustomerNotFoundException;
import com.telecom.b2b.agreementmicroservice.feign.CustomerFeignClient;
import com.telecom.b2b.agreementmicroservice.repository.AgreementRepository;
import com.telecom.b2b.agreementmicroservice.repository.PricePlanRepository;
import com.telecom.b2b.agreementmicroservice.repository.SubscriptionsRepository;
import com.telecom.b2b.agreementmicroservice.service.AgreementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AgreementServiceImpl implements AgreementService {

    @Autowired
    AgreementRepository agreementRepository;
    @Autowired
    SubscriptionsRepository subscriptionsRepository;
    @Autowired
    PricePlanRepository pricePlanRepository;
    @Autowired
    AgreementEventProducer agreementEventProducer;

    @Autowired
    CustomerFeignClient customerFeignClient;


    RestTemplate restTemplate = new RestTemplate();


    @Override
    public Agreement saveAgreement(AgreementRequest agreementRequest) {

        Agreement agreement = new Agreement(agreementRequest);
        agreement.setAgreementCreateDate(new Date());
        agreement = agreementRepository.save(agreement);
        List<Subscriptions> subscriptionsList = new ArrayList<Subscriptions>();
        List<Agreement> exixtingAgreements = getAgreementByCustomerId(agreementRequest.getCustomerId());


        for (SubscriptionsRequest subscriptionsRequest : agreementRequest.getSubscriptions()) {
            Subscriptions subscription = new Subscriptions();
            PricePlans pricePlan = new PricePlans();
            subscription.setAgreement(agreement);
            subscription.setSubscriptionCode(subscriptionsRequest.getSubscriptionCode());
            subscription.setSubscriptionDesc(subscriptionsRequest.getSubscriptionDesc());
            subscription.setSubscriptionCategory(subscriptionsRequest.getSubscriptionCategory());
            subscription.setSubscriptionType(subscriptionsRequest.getSubscriptionType());
            subscription.setSubscriptionEndDate(subscriptionsRequest.getSubscriptionEndDate());
            subscription.setSubscriptionProduct(subscriptionsRequest.getSubscriptionProduct());
            subscription.setSubscriptionStartDate(subscriptionsRequest.getSubscriptionStartDate());
            subscriptionsRepository.save(subscription);

            pricePlan.setPricePlanCode(subscriptionsRequest.getPricePlan().getPricePlanCode());
            pricePlan.setPricePlanDesc(subscriptionsRequest.getPricePlan().getPricePlanDesc());
            pricePlan.setPricePlanProduct(subscriptionsRequest.getPricePlan().getPricePlanProduct());
            pricePlan.setPricePlanEndDate(subscriptionsRequest.getPricePlan().getPricePlanEndDate());
            pricePlan.setPricePlanStartDate(subscriptionsRequest.getPricePlan().getPricePlanStartDate());
            pricePlan.setPricePlanCategory(subscriptionsRequest.getPricePlan().getPricePlanCategory());
            pricePlan.setPricePlanType(subscriptionsRequest.getPricePlan().getPricePlanType());
            pricePlan.setSubscription(subscription);
            pricePlanRepository.save(pricePlan);
            subscription.setPricePlans(pricePlan);
            subscriptionsList.add(subscription);


        }

        agreement.setSubscriptions(subscriptionsList);
        return agreement;

    }


    @Override
    public Agreement saveAgreementNew(Agreement agreement) throws URISyntaxException, IOException {


        String path = "/v1/customer/" + agreement.getCustomerId();
        URI uri = new URI("http", null, "localhost", 7012, path, null, null);
        RequestEntity<Void> request = RequestEntity.get(uri)
                .accept(MediaType.APPLICATION_JSON).build();
        try {

            ResponseEntity<Customer> response = restTemplate.exchange(request, Customer.class);
            Customer customer = new Customer();
            if (response.getStatusCode() != HttpStatus.FOUND) {
                customer = response.getBody();
            } else {
                throw new CustomerNotFoundException("Customer Id is not valid"+agreement.getCustomerId());
            }
        }catch (Exception e){
            throw new CustomerNotFoundException("Customer Id is not valid");
        }
        List<Subscriptions> subscriptionsList = agreement.getSubscriptions();
        for (Subscriptions subscriptions : subscriptionsList) {
            PricePlans pricePlans = subscriptions.getPricePlans();
            pricePlans.setSubscription(subscriptions);
            subscriptions.setPricePlans(pricePlans);
            subscriptions.setAgreement(agreement);
        }
        agreement.setSubscriptions(subscriptionsList);

        agreement = agreementRepository.save(agreement);
        agreementEventProducer.sendAgreementEvent(agreement, AgreementEventType.CREATE);
        return agreement;

    }

    @Override
    public Optional<Agreement> getAgreementById(Long agreementId) {
        Optional<Agreement> agreementData = agreementRepository.findById(agreementId);
        return agreementData;
    }

    @Override
    public List<Agreement> getAgreementByCustomerId(Long customerId) {
        return agreementRepository.findByCustomerId(customerId);
    }

    @Override
    public Customer getCustomerById(Long customerId) throws Exception {
        try {
            String path = "/v1/customer" + "/" + customerId;
            URI uri = new URI("http", null, "localhost", 8082, path, null, null);
            RequestEntity<Void> request = RequestEntity.get(uri).build();

            ResponseEntity<Customer> response = restTemplate.exchange(request, Customer.class);

            if (response.getStatusCode().is4xxClientError()) {
                return null;
            }
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw ex;
            }
            return null;
        }
    }

    @Override
    public AgreementCustomer getAgreementCustomerById(Long agreementId) throws URISyntaxException {
        Optional<Agreement> agreementData = agreementRepository.findById(agreementId);
        AgreementCustomer agreementCustomer= new AgreementCustomer();
        Customer customer = new Customer();

        String path = "/v1/customer/" + agreementData.get().getCustomerId();
        URI uri = new URI("http", null, "localhost", 8082, path, null, null);
        RequestEntity<Void> request = RequestEntity.get(uri)
                .accept(MediaType.APPLICATION_JSON).build();
        try {

            ResponseEntity<Customer> response = restTemplate.exchange(request, Customer.class);

            if (response.getStatusCode() != HttpStatus.FOUND) {
                customer = response.getBody();
            } else {
                throw new CustomerNotFoundException("Customer Id is not valid");
            }
        }catch (Exception e){
            throw new CustomerNotFoundException("Customer Id is not valid");
        }
        agreementCustomer.setAgreement(agreementData.get());
        agreementCustomer.setCustomer(customer);
        return agreementCustomer;
    }



    @Override
    public AgreementCustomer getAgreementCustomerByFeign(Long agreementId){
        log.info("agreementId "+agreementId);
        Optional<Agreement> agreementData = agreementRepository.findById(agreementId);
        AgreementCustomer agreementCustomer= new AgreementCustomer();
        log.info("CustomerId "+agreementData.get().getCustomerId());
        Customer customer =customerFeignClient.getCustomerById(agreementData.get().getCustomerId());
        agreementCustomer.setAgreement(agreementData.get());
        agreementCustomer.setCustomer(customer);
        return agreementCustomer;
    }
}
