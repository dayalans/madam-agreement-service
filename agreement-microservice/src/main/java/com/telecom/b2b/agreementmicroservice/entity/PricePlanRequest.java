package com.telecom.b2b.agreementmicroservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricePlanRequest {
    private String pricePlanCode;
    private String pricePlanDesc;
    private String pricePlanProduct;
    private Date pricePlanStartDate;
    private Date pricePlanEndDate;
    private String pricePlanType;
    private String pricePlanCategory;
}
