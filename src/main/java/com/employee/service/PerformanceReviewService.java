package com.employee.service;

import java.time.LocalDateTime;
import java.util.List;

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
                .orElseThrow(
                        () -> new RuntimeException("Review Cycle not found with ID: " + reviewRequest.reviewCycleId()));
        if (reviewRequest.rating() < 1 || reviewRequest.rating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }
        if (employee.getEmpId().equals(reviewRequest.empId())) {
            throw new RuntimeException("Employee cannot review themselves");
        }
        // multiple reviews in same cycle for same employee should not be allowed
        List<PerformanceReview> existingReviews = performanceReviewRepository.findByEmployeeEmpId(employee.getEmpId());
        boolean alreadyReviewed = existingReviews.stream()
                .anyMatch(r -> r.getReviewCycle().getId().equals(reviewRequest.reviewCycleId()));
        if (alreadyReviewed) {
            throw new RuntimeException("Employee has already been reviewed for this cycle");
        }

        // Create and save the performance review
        PerformanceReview review = new PerformanceReview();
        review.setEmployee(employee);
        review.setReviewCycle(reviewCycle);
        review.setRating(reviewRequest.rating());
        review.setReviewerNotes(reviewRequest.reviewerNotes());
        review.setSubmittedAt(LocalDateTime.now());

        performanceReviewRepository.save(review);

        return new PerformanceReviewResponse(
                review.getId(),
                employee.getEmpId(),
                employee.getEmpName(),
                reviewCycle.getId(),
                reviewCycle.getCycleName(),
                review.getRating(),
                review.getReviewerNotes(),
                review.getSubmittedAt());
    }

    public List<PerformanceReviewResponse> getEmployeeReviews(Long empId) {

        List<PerformanceReview> reviews = performanceReviewRepository.findByEmployeeEmpId(empId);

        return reviews.stream()
                .map(review -> new PerformanceReviewResponse(
                        review.getId(),
                        review.getEmployee().getEmpId(),
                        review.getEmployee().getEmpName(),
                        review.getReviewCycle().getId(),
                        review.getReviewCycle().getCycleName(),
                        review.getRating(),
                        review.getReviewerNotes(),
                        review.getSubmittedAt()))
                .toList();
    }

}
