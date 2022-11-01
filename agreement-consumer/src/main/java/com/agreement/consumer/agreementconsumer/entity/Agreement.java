package com.agreement.consumer.agreementconsumer.entity;


import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "agreement")
public class Agreement {
    @Id
    @Indexed(unique = true)
    private Long agreementId;
    private String agreementStatus;
    private Long customerId;
    private Date agreementStartDate;
    private Date agreementEndDate;
    private String agreementSignerName;
    private Date agreementSignDate;
    private Date agreementCreateDate;
    private Date agreementUpdateDate;
    private List<Subscriptions> subscriptions;




}
