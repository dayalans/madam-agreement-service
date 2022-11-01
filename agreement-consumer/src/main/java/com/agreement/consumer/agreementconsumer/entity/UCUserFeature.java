package com.agreement.consumer.agreementconsumer.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data

public class UCUserFeature {
	private Long ucUserFatureId;
	private String ucUserFatureCode;
	private Date ucUserFatureCrtDt;
	private Date ucUserFatureActDt;
	private UCUser ucUser;
	
	
	
	

}
