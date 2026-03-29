package com.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.model.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer>  {
    
}
