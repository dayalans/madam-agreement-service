package com.agreement.consumer.agreementconsumer.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UCCompany {
	private String ucCompanyId;
	private long ucInstanceId;
	private String ucCompanyName;
	private long ucBillingNumber;
	private Date ucCompanyCreateDate;
	private Date ucCompanyUpdateDate;
	private List<UCUser> ucUser;
	
	

	
}
