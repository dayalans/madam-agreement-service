package com.agreement.consumer.agreementconsumer.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data

@NoArgsConstructor
@Document(collection = "ucinstance")
public class UCInstance {
    @Id
    @Indexed(unique = true)
	private long ucInstanceID;
	private String ucType;
	private Date ucStartDate;
	private Date ucEndDate;
	private String ucOfferCode;
	private String ucVersion;
	private long agreementId;
	private long customerId;
	private UCCompany ucCompany;

}
