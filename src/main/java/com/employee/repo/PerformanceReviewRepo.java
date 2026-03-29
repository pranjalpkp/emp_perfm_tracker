package com.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.model.PerformanceReview;

public interface PerformanceReviewRepo extends JpaRepository<PerformanceReview, Long> {
    
}
