package com.fcamara.minhaVaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.model.MonthlyUser;

@Repository
public interface MonthlyUsersRepository extends JpaRepository<MonthlyUser, Long>{

}
