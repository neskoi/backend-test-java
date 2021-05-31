package com.fcamara.minhaVaga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.models.CarParks;

@Repository
public interface CarParksRepository extends JpaRepository<CarParks, Long> {

}
