package com.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Swagger Desafio técnico java Pitang - API",
		version = "1", description = "API Desenvolvida para testes do desafio java pitang"))
//@EnableDiscoveryClient
public class DesafiojavapitangApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafiojavapitangApiApplication.class, args);
	}

}
