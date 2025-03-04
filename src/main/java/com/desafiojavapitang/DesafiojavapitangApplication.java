package com.desafiojavapitang;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Swagger Desafio técnico java Pitang",
		version = "1", description = "API Desenvolvida para testes do desafio java pitang"))
@EnableDiscoveryClient
public class DesafiojavapitangApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafiojavapitangApplication.class, args);
	}

}
