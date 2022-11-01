package com.telecom.b2b.customermicroservice.repository;

import com.telecom.b2b.customermicroservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,Long>, JpaSpecificationExecutor<Customer> {

}
