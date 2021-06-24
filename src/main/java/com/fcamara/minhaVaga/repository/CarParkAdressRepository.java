package com.fcamara.minhaVaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.model.Adress;

@Repository
public interface CarParkAdressRepository extends JpaRepository<Adress, Long> {

}
