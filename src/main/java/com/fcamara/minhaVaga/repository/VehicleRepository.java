package com.fcamara.minhaVaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fcamara.minhaVaga.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
