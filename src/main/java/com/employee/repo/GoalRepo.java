package com.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.employee.model.Goal;

public interface GoalRepo extends JpaRepository<Goal, Long> {

    @Query("""
            SELECT COUNT(g)
            FROM Goal g
            WHERE g.reviewCycle.id = :cycleId AND g.status = 'COMPLETED'
            """)
    Long countCompletedGoals(Long cycleId);

    @Query("""
            SELECT COUNT(g)
            FROM Goal g
            WHERE g.reviewCycle.id = :cycleId AND g.status = 'MISSED'
            """)
    Long countMissedGoals(Long cycleId);
}
