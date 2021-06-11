package com.fcamara.minhaVaga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fcamara.minhaVaga.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

	Optional<Vehicle> findByPlate(String plate);

}
