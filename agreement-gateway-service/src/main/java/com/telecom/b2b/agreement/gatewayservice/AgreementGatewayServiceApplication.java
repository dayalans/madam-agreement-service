package com.telecom.b2b.agreement.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
@EnableDiscoveryClient
public class AgreementGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgreementGatewayServiceApplication.class, args);
	}
	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/maxmadam/v1/agreement/**")
						.filters(f -> f.rewritePath("/maxmadam/v1/agreement/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time",new Date().toString()))
						.uri("lb://AGREEMENT-SERVICE")).
				route(p -> p
						.path("/maxmadam/v1/customer/**")
						.filters(f -> f.rewritePath("/maxmadam/v1/customer/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time",new Date().toString()))
						.uri("lb://CUSTOMER-SERVICE"))
				.route(p -> p
						.path("/maxmadam/v1/agreementadmin/**")
						.filters(f -> f.rewritePath("/v1/agreementadmin/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time",new Date().toString()))
						.uri("lb://AGREEMENT-ADMIN-MICROSERVICE"))
				.route(p -> p
						.path("/maxmadam/v1/ucinstance/**")
						.filters(f -> f.rewritePath("/v1/ucinstance/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time",new Date().toString()))
						.uri("lb://AGREEMENT-ADMIN-MICROSERVICE"))
				.build();
	}
}
