package com.telecom.b2b.agreementdiscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class AgreementDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgreementDiscoveryServiceApplication.class, args);
	}

}
