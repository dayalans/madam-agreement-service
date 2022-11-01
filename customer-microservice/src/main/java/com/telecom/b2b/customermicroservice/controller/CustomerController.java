package com.telecom.b2b.customermicroservice.controller;

import com.telecom.b2b.customermicroservice.entity.Customer;
import com.telecom.b2b.customermicroservice.entity.SearchCriteria;
import com.telecom.b2b.customermicroservice.service.CustomerService;
import com.telecom.b2b.customermicroservice.specification.CustomerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController

public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/v1/customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid Customer customer)
    {
        customerService.saveCustomer(customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);

    }

    @GetMapping("/v1/customer/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable("customerId") long customerId)
    {
        Optional<Customer> customerData = customerService.getCustomerById(customerId);
        if (customerData.isPresent()) {
            return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Message is not found",HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/v1/customer/")
    public ResponseEntity<?> getCustomerSearch(@RequestBody @Valid SearchCriteria criteria)
    {
        CustomerSpecification specification= new CustomerSpecification();
        specification.add(criteria);
        List<Customer> customerList=customerService.getAllCustomer(specification);

        if (!customerList.isEmpty()) {
            return new ResponseEntity<>(customerList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Message is not found",HttpStatus.NOT_FOUND);
        }

    }
}
