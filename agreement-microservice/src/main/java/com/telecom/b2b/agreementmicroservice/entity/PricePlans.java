package com.telecom.b2b.agreementmicroservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class PricePlans {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "PRICEPLAN_ID")
    private Long pricePlanId;
    private String pricePlanCode;
    private String pricePlanDesc;
    private String pricePlanProduct;
    private Date pricePlanStartDate;
    private Date pricePlanEndDate;
    private String pricePlanType;
    private String pricePlanCategory;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SUBSCRIPTION_ID")
    @JsonBackReference
    private Subscriptions subscription;


}
