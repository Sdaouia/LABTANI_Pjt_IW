package com.ingweb.tp.gatewayService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
	RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()

				.route( r -> r.path("/users/**")
						.uri("lb://userService"))

				.route(r -> r.path("/appointments/**")
						.uri("lb://appointmentService/"))
				
				.route(r -> r.path("/availabilities/**")
						.uri("lb://availabilityService/"))

				.build();

	}

}
