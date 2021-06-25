package com.fcamara.minhaVaga.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fcamara.minhaVaga.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	public Optional<Role> findByName(String name);
}
