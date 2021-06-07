package com.fcamara.minhaVaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.model.CarParkUsage;

@Repository
public interface CarParkUsageRepository extends JpaRepository<CarParkUsage, Long> {

}
