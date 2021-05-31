package com.fcamara.minhaVaga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.models.CarParkUsage;

@Repository
public interface CarParkUsageRepository extends JpaRepository<CarParkUsage, Long> {

}
