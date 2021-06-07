package com.fcamara.minhaVaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long>{
	public User findByEmail(String email);

	public User findByCpf(String cpf);
}
