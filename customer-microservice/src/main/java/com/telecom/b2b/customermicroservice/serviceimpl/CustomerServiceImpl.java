package com.telecom.b2b.customermicroservice.serviceimpl;

import com.telecom.b2b.customermicroservice.entity.Customer;
import com.telecom.b2b.customermicroservice.entity.CustomerAddress;
import com.telecom.b2b.customermicroservice.repository.CustomerRepository;
import com.telecom.b2b.customermicroservice.service.CustomerService;
import com.telecom.b2b.customermicroservice.specification.CustomerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Long saveCustomer(Customer customer) {
        CustomerAddress customerAddress=customer.getCustomerAddress();
        customer.setCustomerCreateDate(new Date());
        customerAddress.setCustomer(customer);
        customerRepository.save(customer);
        return customer.getCustomerId();
    }

    @Override
    public Optional<Customer> getCustomerById(Long customerId) {
        Optional<Customer> customerData= customerRepository.findById(customerId);
        return  customerData;
    }

    @Override
    public List<Customer> getAllCustomer(CustomerSpecification customerSpecification) {
        return customerRepository.findAll(customerSpecification);
    }


}
