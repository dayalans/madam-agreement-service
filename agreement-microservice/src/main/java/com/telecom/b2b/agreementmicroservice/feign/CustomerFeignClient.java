package com.telecom.b2b.agreementmicroservice.feign;


import com.telecom.b2b.agreementmicroservice.entity.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.ws.rs.core.Application;

@FeignClient("customer-service")
public interface CustomerFeignClient {
    @RequestMapping(method = RequestMethod.GET,value="/v1/customer/{customerId}",consumes = "application/json")
    Customer getCustomerById(@PathVariable("customerId") long customerId);
}
