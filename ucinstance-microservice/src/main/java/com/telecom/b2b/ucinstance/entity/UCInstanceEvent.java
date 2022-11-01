package com.telecom.b2b.ucinstance.entity;

import lombok.Data;

@Data
public class UCInstanceEvent {
	private String ucInstanceEventType;
	private UCInstance ucInstance;
	private UCCompany ucCompany;
	private UCUser ucUser;

}
