package com.agreement.consumer.agreementconsumer.entity;

import lombok.Data;

@Data
public class UCInstanceEvent {
	private String ucInstanceEventType;
	private UCInstance ucInstance;
	private UCCompany ucCompany;
	private UCUser ucUser;
}
