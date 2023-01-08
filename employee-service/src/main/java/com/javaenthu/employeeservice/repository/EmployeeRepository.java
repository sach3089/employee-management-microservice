package com.javaenthu.employeeservice.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javaenthu.employeeservice.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	Page<Employee> findAll(Pageable pageable);
    
	@Query("select emp from Employee emp where emp.name like %:search% or emp.bloodGroup like %:search% or emp.email like %:search%")
	Page<Employee> findByNameOrBloodGroupOrEmail(Pageable pageable, String search);

}
