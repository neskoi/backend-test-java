package com.fcamara.minhaVaga.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vaga")
public class CarParkUsageController {

	@PostMapping("/estacionar/{park}/{vehicle}")
	public String parking(@PathVariable(value = "park") int park_id, @PathVariable(value = "vehicle") int vehicle_id) {
		return "Estacionou no estacionamento: " + park_id + " o carro " + vehicle_id;
	}
	
	@PostMapping("/partir/{park}/{vehicle}")
	public String leave(@PathVariable(value = "park") int park_id, @PathVariable(value = "vehicle") int vehicle_id) {
		return "Saiu do estacionamento: " + park_id + " o carro " + vehicle_id;
	}
}
