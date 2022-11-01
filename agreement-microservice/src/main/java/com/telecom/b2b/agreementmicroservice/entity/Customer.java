package com.telecom.b2b.agreementmicroservice.entity;

import lombok.*;



import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer {

    private Long customerId;
    private String  customerCode;
    private String  customerName;
    private String  customerRegistrationDate;
    private String  customerStatus;
    private Date    customerCreateDate;
    private Date    customerUpdateDate;

}
