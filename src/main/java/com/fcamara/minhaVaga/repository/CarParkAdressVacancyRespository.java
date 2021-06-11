package com.fcamara.minhaVaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.model.Vacancy;

@Repository
public interface CarParkAdressVacancyRespository extends JpaRepository<Vacancy, Long> {

}
