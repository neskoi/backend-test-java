package com.fcamara.minhaVaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.model.CarPark;

@Repository
public interface CarParksRepository extends JpaRepository<CarPark, Long> {

}
