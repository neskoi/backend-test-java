package com.fcamara.minhaVaga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.models.MonthlyUsers;

@Repository
public interface MonthlyUsersRepository extends JpaRepository<MonthlyUsers, Long>{

}
