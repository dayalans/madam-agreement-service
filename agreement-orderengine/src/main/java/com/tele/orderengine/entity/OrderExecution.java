package com.tele.orderengine.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class OrderExecution {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDEREXECUTION_ID")
	private long orderExecutionId;
	@Column(name = "ORDERMAP_ID")
	private long orderMapId;
	@Column(name = "ORDEREXECUTION_STEP")
	private String orderExecutionStep;
	@Column(name = "ORDEREXECUTION_STATUS")
	private String orderExecutionStatus;
	@Column(name = "ORDEREXECUTION_FAILURE_REASON")
	private String orderExecutionFailureReason;
	@Column (name="ORDEREXECUTION_CREATE_DATE")
	private Date orderExecutionCreateDate;	
	@Column (name="ORDEREXECUTION_UPDATE_DATE")
	private Date orderExecutionUpdateDate;

	

}
