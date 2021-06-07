package com.fcamara.minhaVaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.model.Vehicle;

@Repository
public interface VehiclesRepository extends JpaRepository<Vehicle, Long> {

}
