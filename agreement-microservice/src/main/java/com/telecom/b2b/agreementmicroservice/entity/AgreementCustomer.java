package com.telecom.b2b.agreementmicroservice.entity;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class AgreementCustomer {
      private Agreement agreement;
      private Customer customer;
}
