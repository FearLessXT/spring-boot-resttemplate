package com.example.springbootresttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringBootRestTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestTemplateApplication.class, args);
	}

	//Inject RestTemplate to Application Layer
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
