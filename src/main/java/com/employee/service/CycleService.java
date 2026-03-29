package com.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.dto.response.CycleSummaryResponse;
import com.employee.model.ReviewCycle;
import com.employee.repo.GoalRepo;
import com.employee.repo.PerformanceReviewRepo;
import com.employee.repo.ReviewCycleRepo;

@Service
public class CycleService {
    @Autowired
    ReviewCycleRepo reviewCycleRepo;
    @Autowired
    PerformanceReviewRepo performanceReviewRepo;
    @Autowired
    GoalRepo goalRepo;

    public CycleSummaryResponse getCycleSummary(Long cycleId) {

    ReviewCycle cycle = reviewCycleRepo.findById(cycleId)
            .orElseThrow(() -> new RuntimeException("Cycle not found"));

    Double avgRating = performanceReviewRepo.findAverageRatingByCycle(cycleId);

    String topPerformer = performanceReviewRepo.findTopPerformer(cycleId);

    Long completedGoals = goalRepo.countCompletedGoals(cycleId);

    Long missedGoals = goalRepo.countMissedGoals(cycleId);

    return new CycleSummaryResponse(
            cycle.getId(),
            cycle.getCycleName(),
            avgRating,
            topPerformer,
            completedGoals,
            missedGoals
    );
}
}
