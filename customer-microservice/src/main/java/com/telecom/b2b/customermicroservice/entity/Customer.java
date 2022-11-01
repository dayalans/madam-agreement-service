package com.telecom.b2b.customermicroservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Customer {
	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQUENCE")
	    @SequenceGenerator(sequenceName = "customer_sequence", allocationSize = 1, name = "CUSTOMER_SEQUENCE")
    @Column(name = "CUSTOMER_ID")
    private Long customerId;
    @Column(name = "CUSTOMER_CODE")
    private String  customerCode;
    @Column(name = "CUSTOMER_NAME")
    private String  customerName;
    @Column(name = "CUSTOMER_REGDATE")
    private String  customerRegistrationDate;
    @Column(name = "CUSTOMER_STATUS")
    private String  customerStatus;
    @Column(name = "CUSTOMER_CREATEDATE")
    private Date    customerCreateDate;
    @Column(name = "CUSTOMER_UPDATEDATE")
    private Date    customerUpdateDate;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
    @JsonManagedReference
    private CustomerAddress customerAddress;
}
