package com.telecom.b2b.agreementmicroservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionsRequest {
    private String subscriptionCode;
    private String subscriptionDesc;
    private Date subscriptionStartDate;
    private Date subscriptionEndDate;
    private String subscriptionProduct;
    private String subscriptionType;
    private String subscriptionCategory;
    private PricePlanRequest pricePlan;
}
