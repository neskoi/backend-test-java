package com.fcamara.minhaVaga.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcamara.minhaVaga.models.CarParks;

@RestController
@RequestMapping("/estacionamento")
public class CarParksController {
	
	@GetMapping("/listar/parametro")
	public CarParks listOneCarParks(){
		return null;
	}
	
	@GetMapping("/listarTodos")
	public List<CarParks> listAllCarParks(){
		return null;
	}
	
	@PostMapping("/registrar")
	public CarParks registerCarPark() {
		return null;
	};
	
	@PutMapping("/modificar/senha")
	public String modifyPassword() {
		return null;
	};
	
	@PutMapping("/modificar/valorHora")
	public BigDecimal modifyHourPrice() {
		return null;
	};
	
	@PutMapping("/modificar/valorMensal")
	public BigDecimal modifyMonthlyPrice() {
		return null;
	};
	
	@PutMapping("/modificar/vagasCarro")
	public int modifyCarVancies() {
		return 0;
	};
	
	@PutMapping("/modificar/vagasMoto")
	public int modifyMotorcycleVacancies() {
		return 0;
	};
	
	@PutMapping("/modificar/telefone")
	public String modifyPhone() {
		return null;
	};
	
}
