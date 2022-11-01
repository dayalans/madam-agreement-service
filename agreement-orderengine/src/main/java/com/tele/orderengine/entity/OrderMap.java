package com.tele.orderengine.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Entity
@Data
public class OrderMap {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDERMAP_ID")
	private Long orderMapId;

	@Column(name = "ORDERMAP_SYSTEM")
	private String orderSystem;
	
	@Column(name = "ORDERMAP_UCINSTANCEID")
	private Long orderUCInstanceID;

	@Column(name = "ORDERMAP_DATA")
	@Lob
	public String orderDetails;
	
	@Column(name="ORDERMAP_NAME")
	private String orderMapName;
	
	@Column (name="ORDERMAP_ACTION")
	private String ordermapAction;
	
	@Column (name="ORDERMAP_STATUS")
	private String ordermapStatus;
	
	@Column (name="ORDERMAP_CREATE_DATE")
	private Date orderMapCreateDate;
	
	@Column (name="ORDERMAP_UPDATE_DATE")
	private Date orderMapUpdateDate;

}
