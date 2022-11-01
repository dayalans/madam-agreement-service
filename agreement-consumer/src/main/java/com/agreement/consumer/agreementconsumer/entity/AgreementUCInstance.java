package com.agreement.consumer.agreementconsumer.entity;

import java.util.List;

import lombok.Data;

@Data
public class AgreementUCInstance {
private Agreement Agreement;
private List<UCInstance> ucInstance;

}
