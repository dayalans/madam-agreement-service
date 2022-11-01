package com.telecom.admin.service;

import static java.util.stream.Collectors.toList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.telecom.admin.entity.PricePlan;
import com.telecom.admin.entity.Subscription;
import com.telecom.admin.exception.PricePlanAlreadyExistsException;
import com.telecom.admin.exception.SubscriptionNotFoundException;
import com.telecom.admin.repository.PricePlanRepository;
import com.telecom.admin.repository.SubscriptionRepository;
import com.telecom.admin.utilities.CSVHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AgreementAdminService {
	
	@Autowired
	SubscriptionRepository subscriptionRepository;
	@Autowired
	PricePlanRepository pricePlanRepository;
	
	
	@Cacheable("subscriptions")
	public List<Subscription> findAll() {
		log.info("AgreementAdminService: findAll");
		List<Subscription> subscriptions = subscriptionRepository.findAll();
		if(subscriptions.isEmpty()) {
			throw new SubscriptionNotFoundException("");
		}
		return subscriptions;
	}
	
	@Cacheable("subscription")
    public Optional<Subscription> findBysubscriptionCode(String subscriptionCode){
        log.info("AgreementAdminService: findBysubscriptionCode");
       Optional<Subscription> subscriptionNew= subscriptionRepository.findBysubscriptionCode(subscriptionCode);
       if(subscriptionNew.isPresent()) {
    	   return subscriptionNew;
       }else {
    	   throw new SubscriptionNotFoundException(subscriptionCode);
       }
	
        
    }
	
	@Caching(evict = {
            @CacheEvict(value="subscription", allEntries=true),
            @CacheEvict(value="subscriptions", allEntries=true)})
	
	public Subscription saveOrUpdate(Subscription subscription){
        log.info("AgreementAdminService: saveOrUpdate, {}", subscription.getSubscriptionCode());
       
        return subscriptionRepository.save(subscription);
    }
	
	@Caching(evict = {
            @CacheEvict(value="priceplans", allEntries=true),
            @CacheEvict(value="activepriceplans", allEntries=true)})
	public void save(MultipartFile file) {
	    try {
	      List<PricePlan> pricePlans = CSVHelper.csvToPricePlans(file.getInputStream());
	      
	      List<PricePlan> matchingPricePlans = pricePlans.stream()
	    	        .filter(f -> getAllActivePricePlans().stream()
	    	                .anyMatch(t -> t.getPricePlanCode().equals(f.getPricePlanCode())))
	    	        .collect(Collectors.toList());
	      if(matchingPricePlans.isEmpty()) {
	    	  pricePlanRepository.saveAll(pricePlans);
	      }else {
	    	  throw new PricePlanAlreadyExistsException(matchingPricePlans.stream().map(PricePlan::getPricePlanCode).collect(toList()).toString());
	      }
	      
	      
	      
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	    }
	  }
	
	@Cacheable("priceplans")
	public List<PricePlan> getAllPricePlans() {
	    return pricePlanRepository.findAll();
	  }
	
	@Cacheable("activepriceplans")
	public List<PricePlan> getAllActivePricePlans() {
	    return pricePlanRepository.findAllActivePricePlans();
	  }
	
	 public ByteArrayInputStream downloadPricePlans() {
		    List<PricePlan> pricePlans = pricePlanRepository.findAll();
		    ByteArrayInputStream in = CSVHelper.tutorialsToCSV(pricePlans);
		    return in;
		  }
}
