package com.telecom.b2b.agreementmicroservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Agreement {
    
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AGREEMENT_SEQUENCE")
    @SequenceGenerator(sequenceName = "agreement_sequence", allocationSize = 1, name = "AGREEMENT_SEQUENCE")
    @Column(name = "AGREEMENT_ID")
    private Long agreementId;
    @Column(name = "AGREEMENT_TYPE")
    private String agreementType;
    @Column(name = "AGREEMENT_STATUS")
    private String agreementStatus;
    @Column(name = "CUSTOMER_ID")
    private Long customerId;
    @Column(name = "AGREEMENT_STARTDATE")
    private Date agreementStartDate;
    @Column(name = "AGREEMENT_ENDDATE")
    private Date agreementEndDate;
    @Column(name = "AGREEMENT_SIGNERNAME")
    private String agreementSignerName;
    @Column(name = "AGREEMENT_SIGNDATE")
    private Date agreementSignDate;
    @Column(name = "AGREEMENT_CREATEDATE")
    private Date agreementCreateDate;
    @Column(name = "AGREEMENT_UPDATEDATE")
    private Date agreementUpdateDate;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "agreement", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("agreement")
    private List<Subscriptions> subscriptions;
    @Transient
    @JsonIgnore
    AgreementEventType agreeEventType;

    public Agreement(AgreementRequest agreementRequest) {
		// TODO Auto-generated constructor stub
	}

}
