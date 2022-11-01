package com.telecom.b2b.agreementmicroservice.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AgreementEvent {
    private Agreement agreement;
    private AgreementEventType agreementEventType;
}
