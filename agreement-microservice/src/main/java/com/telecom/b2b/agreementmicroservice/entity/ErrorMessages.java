package com.telecom.b2b.agreementmicroservice.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ErrorMessages {
    private Date exceptionDate;
    private String errorMessage;
}
