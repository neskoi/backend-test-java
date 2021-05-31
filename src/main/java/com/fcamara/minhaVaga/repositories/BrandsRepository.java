package com.fcamara.minhaVaga.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fcamara.minhaVaga.models.Brands;

@Repository
public interface BrandsRepository extends JpaRepository<Brands, Long>{

}
