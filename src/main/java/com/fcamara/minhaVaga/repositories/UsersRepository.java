package com.fcamara.minhaVaga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.models.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{
	
}
