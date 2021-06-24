package com.fcamara.minhaVaga.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fcamara.minhaVaga.model.CarPark;
import com.fcamara.minhaVaga.repository.CarParkRepository;

@Service
public class CarParkAuthService  implements UserDetailsService {

	@Autowired
	CarParkRepository carParksRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<CarPark> searchedCarPark = carParksRepository.findByEmail(email);
		if(searchedCarPark.isPresent())
				return searchedCarPark.get();
		throw new UsernameNotFoundException("Dados invalidos.");
	}

}