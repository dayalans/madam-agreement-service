package com.telecom.b2b.ucinstance.entity;

import java.util.Date;

import lombok.Data;

@Data
public class OrderMap {

	private Long orderMapId;

	private String orderSystem;

	public String orderDetails;

	private String orderMapName;

	private String ordermapAction;

	private String ordermapStatus;

	private Date orderMapCreateDate;

	private Date orderMapUpdateDate;

}
