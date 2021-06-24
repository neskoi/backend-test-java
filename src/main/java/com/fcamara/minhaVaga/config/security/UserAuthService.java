package com.fcamara.minhaVaga.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.repository.UserRepository;

@Service
public class UserAuthService implements UserDetailsService {

	@Autowired
	UserRepository usersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> searchedUser = usersRepository.findByEmail(email);
		if(searchedUser.isPresent())
				return searchedUser.get();
		throw new UsernameNotFoundException("Dados invalidos.");
	}

}
