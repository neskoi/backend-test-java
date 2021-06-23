package com.fcamara.minhaVaga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableSpringDataWebSupport
@OpenAPIDefinition(info = 
	@Info(
			title = "Minha Vaga API",
			description =  "Uma pequena api para gerenciamento de vagas de estacionamento "
					+ "desenvolvida a título de estudo.",
			version = "0.0.1",
			contact = @Contact(
					name = "Jan Nes",
					email = "jan.nes@fcamara.com.br",
					url = "https://www.linkedin.com/in/nesjan/" )))
public class MinhaVagaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinhaVagaApplication.class, args);
	}

}
