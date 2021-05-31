package com.fcamara.minhaVaga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.models.Models;

@Repository
public interface ModelsRepository extends JpaRepository<Models, Long> {

}
