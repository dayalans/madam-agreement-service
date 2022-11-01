package com.telecom.b2b.ucinstance.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UCUserFeature {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "UCUSER_FEATUREID")
	private Long ucUserFatureId;
	@Column(name = "UCUSER_FEATURECODE")
	private String ucUserFatureCode;
	@Column(name = "UCUSER_FEATURECRTDT")
	private Date ucUserFatureCrtDt;
	@Column(name = "UCUSER_FEATUREACTDT")
	private Date ucUserFatureActDt;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "UCUSER_ID",nullable = false)
    @JsonIgnoreProperties("ucUserFeature")
	private UCUser ucUser;
	
	
	
	

}
