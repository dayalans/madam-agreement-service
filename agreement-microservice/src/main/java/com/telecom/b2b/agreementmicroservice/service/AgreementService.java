package com.telecom.b2b.agreementmicroservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telecom.b2b.agreementmicroservice.entity.Agreement;
import com.telecom.b2b.agreementmicroservice.entity.AgreementCustomer;
import com.telecom.b2b.agreementmicroservice.entity.AgreementRequest;
import com.telecom.b2b.agreementmicroservice.entity.Customer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public interface AgreementService {
    public Agreement saveAgreement(AgreementRequest agreementRequest);

    Agreement saveAgreementNew(Agreement agreement) throws URISyntaxException, IOException;

    public Optional<Agreement> getAgreementById(Long agreementId);
    public List<Agreement> getAgreementByCustomerId(Long customerId);
    public Customer getCustomerById(Long customerId) throws Exception;
    public AgreementCustomer getAgreementCustomerById(Long agreementId) throws URISyntaxException;

    AgreementCustomer getAgreementCustomerByFeign(Long agreementId);
}
