package com.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableEurekaClient
@EnableZuulProxy
public class ApiGatewayApplication {
	public static void main(String[] args) {

		SpringApplication.run(ApiGatewayApplication.class, args);
		
	}

}
