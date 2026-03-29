package com.employee.service;

import org.springframework.stereotype.Service;

import com.employee.dto.request.PerformanceReviewRequest;
import com.employee.dto.response.PerformanceReviewResponse;
import com.employee.model.Employee;
import com.employee.model.PerformanceReview;
import com.employee.model.ReviewCycle;
import com.employee.repo.EmployeeRepo;
import com.employee.repo.PerformanceReviewRepo;
import com.employee.repo.ReviewCycleRepo;

@Service
public class PerformanceReviewService {
    private final PerformanceReviewRepo performanceReviewRepository;
    private final EmployeeRepo employeeRepository;
    private final ReviewCycleRepo reviewCycleRepository;

    public PerformanceReviewService(PerformanceReviewRepo performanceReviewRepository, EmployeeRepo employeeRepository,
            ReviewCycleRepo reviewCycleRepository) {
        this.performanceReviewRepository = performanceReviewRepository;
        this.employeeRepository = employeeRepository;
        this.reviewCycleRepository = reviewCycleRepository;
    }

public PerformanceReviewResponse submitReview(PerformanceReviewRequest reviewRequest) {
    
    Employee employee = employeeRepository.findById(reviewRequest.empId())
            .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + reviewRequest.empId()));
    
    ReviewCycle reviewCycle = reviewCycleRepository.findById(reviewRequest.reviewCycleId())
            .orElseThrow(() -> new RuntimeException("Review Cycle not found with ID: " + reviewRequest.reviewCycleId()));

    // Create and save the performance review
    PerformanceReview review = new PerformanceReview();
    review.setEmployee(employee);
    review.setReviewCycle(reviewCycle);
    review.setRating(reviewRequest.rating());
    review.setReviewerNotes(reviewRequest.reviewerNotes());
    review.setSubmittedAt(reviewRequest.submittedAt());

    performanceReviewRepository.save(review);

    return new PerformanceReviewResponse(
        review.getId(),
        employee.getEmpName(),
        reviewCycle.getCycleName(),
        review.getRating(),
        review.getReviewerNotes(),
        review.getSubmittedAt()
    );

    };

}
