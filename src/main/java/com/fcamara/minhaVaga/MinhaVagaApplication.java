package com.fcamara.minhaVaga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class MinhaVagaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinhaVagaApplication.class, args);
	}

}
