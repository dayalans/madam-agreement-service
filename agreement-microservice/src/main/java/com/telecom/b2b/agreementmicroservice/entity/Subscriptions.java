package com.telecom.b2b.agreementmicroservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Subscriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SUBSCRIPTION_ID")
    private Long subscriptionId;
    private String subscriptionCode;
    private String subscriptionDesc;
    private Date subscriptionStartDate;
    private Date subscriptionEndDate;
    private String subscriptionProduct;
    private String subscriptionType;
    private String subscriptionCategory;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "AGREEMENT_ID",nullable = false)
    @JsonIgnoreProperties("subscriptions")
    private Agreement agreement;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "subscription")
    @JsonManagedReference
    private PricePlans pricePlans;





    public Subscriptions(String subscriptionCode, String subscriptionDesc, Date subscriptionStartDate, Date subscriptionEndDate, String subscriptionProduct, String subscriptionType, String subscriptionCategory, Agreement finalAgreement) {
    }
}
