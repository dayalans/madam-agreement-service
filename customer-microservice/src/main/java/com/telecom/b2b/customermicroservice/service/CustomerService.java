package com.telecom.b2b.customermicroservice.service;

import com.telecom.b2b.customermicroservice.entity.Customer;
import com.telecom.b2b.customermicroservice.specification.CustomerSpecification;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    public Long saveCustomer(Customer customer);
    public Optional<Customer> getCustomerById(Long customerId);
    public List<Customer> getAllCustomer(CustomerSpecification customerSpecification);
}
