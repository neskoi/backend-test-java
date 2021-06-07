package com.fcamara.minhaVaga.repository;

import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.model.Brand;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BrandsRepository extends JpaRepository<Brand, Long>{

}
