package com.telecom.b2b.ucinstance.entity;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UCInstance {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UCINSTANCEID_SEQUENCE")
    @SequenceGenerator(sequenceName = "ucinstanceid_sequence", allocationSize = 1, name = "UCINSTANCEID_SEQUENCE")
    @Column(name = "UCINSTANCE_ID")
	private long ucInstanceID;
	 @NotNull(message = "INVALID_UCTYPE")
	@Column(name = "UCTYPE")
	private String ucType;
	 @NotNull
	 @Past(message = "INVALID_UCSTART_DATE")
	@Column(name = "UCSTART_DATE") 
	private Date ucStartDate;
	 @NotNull
	 @Past(message = "INVALID_UCEND_DATE")
	@Column(name = "UCEND_DATE")
	private Date ucEndDate;
	 @NotNull(message = "INVALID_UCOFFER_CODE")
	@Column(name = "UCOFFER_CODE")
	private String ucOfferCode;
	 @NotNull(message = "INVALID_UCVERSION")
	@Column(name = "UCVERSION")
	private String ucVersion;
	 @NotNull(message = "INVALID_AGREEMENT_ID")
	@Column(name = "AGREEMENT_ID")
	private long agreementId;
	 @NotNull(message = "INVALID_CUSTOMER_ID")
	@Column(name = "CUSTOMER_ID")
	private long customerId;

}
