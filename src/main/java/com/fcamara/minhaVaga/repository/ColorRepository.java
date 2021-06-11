package com.fcamara.minhaVaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.model.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

}
