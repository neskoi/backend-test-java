package com.fcamara.minhaVaga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.models.Colors;

@Repository
public interface ColorsRepository extends JpaRepository<Colors, Long> {

}
