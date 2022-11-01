package com.telecom.b2b.agreementmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.ControllerAdvice;

@SpringBootApplication
@EnableFeignClients
@ControllerAdvice(basePackages = {"com.telecom.b2b.agreementmicroservice.advice"} ) 
public class AgreementMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgreementMicroserviceApplication.class, args);
	}

}
