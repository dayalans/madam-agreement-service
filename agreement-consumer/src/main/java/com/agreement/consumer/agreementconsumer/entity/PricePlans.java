package com.agreement.consumer.agreementconsumer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PricePlans {
    private Long pricePlanId;
    private String pricePlanCode;
    private String pricePlanDesc;
    private String pricePlanProduct;
    private Date pricePlanStartDate;
    private Date pricePlanEndDate;
    private String pricePlanType;
    private String pricePlanCategory;


}
