package com.desafiojavapitang_registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DesafiojavapitangRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafiojavapitangRegistryApplication.class, args);
	}

}
