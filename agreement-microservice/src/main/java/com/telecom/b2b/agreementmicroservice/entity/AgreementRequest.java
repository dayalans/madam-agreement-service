package com.telecom.b2b.agreementmicroservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgreementRequest {
    private String agreementType;
    private String agreementStatus;
    private Long customerId;
    private Date agreementStartDate;
    private Date agreementEndDate;
    private String agreementSignerName;
    private Date agreementSignDate;
    private Date agreementCreateDate;
    private Date agreementUpdateDate;
    private List<SubscriptionsRequest> subscriptions;
}
