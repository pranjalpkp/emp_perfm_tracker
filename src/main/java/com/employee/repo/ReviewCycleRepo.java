package com.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.model.ReviewCycle;

public interface ReviewCycleRepo extends JpaRepository<ReviewCycle, Long> {
    
}
