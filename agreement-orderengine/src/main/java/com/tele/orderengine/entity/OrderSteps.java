package com.tele.orderengine.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class OrderSteps {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDERSTEP_ID")
	private Long orderStepId;
	@Column(name = "ORDERSTEP_OBJECT")
	private String orderStepObject;
	@Column(name = "ORDERSTEP_ACTION")
	private String orderStepAction;
	@Column(name = "ORDERSTEP_NAME")
	private String orderStepName;
	@Column(name = "ORDERSTEP_TOPIC")
	private String orderStepTopic;
	@Column(name = "ORDERSTEP_TYPE")
	private String orderStepType;

}
