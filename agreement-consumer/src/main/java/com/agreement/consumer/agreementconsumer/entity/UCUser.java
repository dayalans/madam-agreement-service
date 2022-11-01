package com.agreement.consumer.agreementconsumer.entity;

import java.util.List;

import lombok.Data;

@Data
public class UCUser {
	private Long ucUserId;
	private String ucCompanyId;
	private Long ucUserMSISDN;
	private Long ucUserFixednr;
	private List<UCUserFeature> ucUserFeature;

}
