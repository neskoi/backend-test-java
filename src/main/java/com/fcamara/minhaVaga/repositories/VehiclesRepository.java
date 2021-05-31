package com.fcamara.minhaVaga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.models.Vehicles;

@Repository
public interface VehiclesRepository extends JpaRepository<Vehicles, Long> {

}
