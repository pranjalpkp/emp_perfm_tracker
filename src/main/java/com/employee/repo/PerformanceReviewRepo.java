package com.employee.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.employee.model.PerformanceReview;

public interface PerformanceReviewRepo extends JpaRepository<PerformanceReview, Long> {

    List<PerformanceReview> findByEmployeeEmpId(Long empId);

    @Query("""
            SELECT AVG(r.rating)
            FROM PerformanceReview r
            WHERE r.reviewCycle.id = :cycleId
            """)
    Double findAverageRatingByCycle(Long cycleId);

    @Query("""
            SELECT r.employee.empName
            FROM PerformanceReview r
            WHERE r.reviewCycle.id = :cycleId
            GROUP BY r.employee.empName
            ORDER BY AVG(r.rating) DESC
            LIMIT 1
            """)
    String findTopPerformer(Long cycleId);
}
