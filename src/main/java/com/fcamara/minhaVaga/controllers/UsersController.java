package com.fcamara.minhaVaga.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcamara.minhaVaga.models.Users;
import com.fcamara.minhaVaga.repositories.UsersRepository;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersRepository usersrepository;
	
	@GetMapping("/id")
	public List<Users> listAllUsers(){
		return  usersrepository.findAll();
	}
}
