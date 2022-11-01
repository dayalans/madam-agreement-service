package com.agreement.consumer.agreementconsumer.entity;

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
