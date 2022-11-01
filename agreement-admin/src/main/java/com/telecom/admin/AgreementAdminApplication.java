package com.telecom.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.ControllerAdvice;

@SpringBootApplication
@EnableCaching
@ControllerAdvice(basePackages = {"com.telecom.admin.advice"} ) 
public class AgreementAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgreementAdminApplication.class, args);
	}

}
