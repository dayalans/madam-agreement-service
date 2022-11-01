package com.telecom.b2b.ucinstance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UCCompany {
	@Id
	@Column(name = "UCCOMPANY_ID")
	private String ucCompanyId;
	private long ucInstanceId;
	@Column(name = "UCCOMPANY_NAME")
	private String ucCompanyName;
	@Column(name = "UCCOMPANY_BILLING_NR")
	private long ucBillingNumber;
	@Column(name = "UCCOMPANY_CREATE_DATE")
	private Date ucCompanyCreateDate;
	@Column(name = "UCCOMPANY_UPDATE_DATE")
	private Date ucCompanyUpdateDate;
	
	
	
	
	public String getUcCompanyId() {
		return ucCompanyId;
	}
	public void setUcCompanyId(String ucCompanyId) {
		this.ucCompanyId = ucCompanyId;
	}
	public long getUcInstanceId() {
		return ucInstanceId;
	}
	public void setUcInstanceId(long ucInstanceId) {
		this.ucInstanceId = ucInstanceId;
	}
	public String getUcCompanyName() {
		return ucCompanyName;
	}
	public void setUcCompanyName(String ucCompanyName) {
		this.ucCompanyName = ucCompanyName;
	}
	public long getUcBillingNumber() {
		return ucBillingNumber;
	}
	public void setUcBillingNumber(long ucBillingNumber) {
		this.ucBillingNumber = ucBillingNumber;
	}
	public Date getUcCompanyCreateDate() {
		return ucCompanyCreateDate;
	}
	public void setUcCompanyCreateDate(Date ucCompanyCreateDate) {
		this.ucCompanyCreateDate = ucCompanyCreateDate;
	}
	public Date getUcCompanyUpdateDate() {
		return ucCompanyUpdateDate;
	}
	public void setUcCompanyUpdateDate(Date ucCompanyUpdateDate) {
		this.ucCompanyUpdateDate = ucCompanyUpdateDate;
	}

	
}
