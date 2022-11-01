package com.agreement.consumer.agreementconsumer.entity;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Subscriptions {
    private Long subscriptionId;
    private String subscriptionCode;
    private String subscriptionDesc;
    private Date subscriptionStartDate;
    private Date subscriptionEndDate;
    private String subscriptionProduct;
    private String subscriptionType;
    private String subscriptionCategory;
    private PricePlans pricePlans;
}
