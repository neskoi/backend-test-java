package com.fcamara.minhaVaga.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mensalidade")
public class MonthlyUsersController {
	
	@PostMapping("/pagar")
	public boolean payMonth() {
		return false;
	}
}
