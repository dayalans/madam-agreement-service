package com.telecom.b2b.ucinstance.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UCUser {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "UCUSER_ID")
	private Long ucUserId;
	@Column(name = "UCCOMPANY_ID")
	private String ucCompanyId;
	@Column(name = "UCUSER_MSISDN")
	private Long ucUserMSISDN;
	@Column(name = "UCUSER_FIXEDNR")
	private Long ucUserFixednr;
	@Column(name = "UCUSER_CREATEDATE")
	private Date ucUserCreateDate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ucUser", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("ucUser")
	private List<UCUserFeature> ucUserFeature;

}
