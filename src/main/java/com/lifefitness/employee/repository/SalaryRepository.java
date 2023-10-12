package com.lifefitness.employee.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.lifefitness.employee.entity.Salary;

public interface SalaryRepository extends JpaRepository<Salary, Long>{
	
	}
