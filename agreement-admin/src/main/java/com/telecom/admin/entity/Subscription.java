package com.telecom.admin.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SUBSCRIPTION_ID")
	private Long subscriptionId;
	@NotNull(message = "INVALID_SUBSCRIPTIONCODE")
	private String subscriptionCode;
	@NotNull(message = "INVALID_SUBSCRIPTIONDESC")
	private String subscriptionDesc;
	@NotNull(message = "INVALID_SUBSCRIPTIONSTARTDATE_NULL")
	 @Future(message = "INVALID_SUBSCRIPTIONSTARTDATE")
	private Date subscriptionStartDate;
	private Date subscriptionEndDate;
	@NotNull(message = "INVALID_SUBSCRIPTIONPRODUCT")
	private String subscriptionProduct;
	@NotNull(message = "INVALID_SUBSCRIPTIONTYPE")
	private String subscriptionType;
	@NotNull(message = "INVALID_SUBSCRIPTIONCATEGORY")
	private String subscriptionCategory;

}
