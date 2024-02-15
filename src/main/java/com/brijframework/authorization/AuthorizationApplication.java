package com.brijframework.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2
public class AuthorizationApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(AuthorizationApplication.class, args);
	}

}
