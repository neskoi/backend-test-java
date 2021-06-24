package com.fcamara.minhaVaga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.model.CarPark;

@Repository
public interface CarParkRepository extends JpaRepository<CarPark, Long> {
	
	public Optional<CarPark> findByCnpj(String cnpj);
	public Optional<CarPark> findByEmail(String email);

}
