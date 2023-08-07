package com.example.apiGlosor;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@OpenAPIDefinition don´t know if this is needed when using swagger UI
public class ApiGlosorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGlosorApplication.class, args);
	}

}
