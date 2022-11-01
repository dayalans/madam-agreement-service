package com.telecom.admin.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PricePlan {
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
    @Column(name = "PRICEPLAN_STATUS")
    private String pricePlanStatus;


}
