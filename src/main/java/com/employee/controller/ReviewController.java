package com.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.dto.request.PerformanceReviewRequest;
import com.employee.dto.response.PerformanceReviewResponse;
import com.employee.service.PerformanceReviewService;

@RestController
public class ReviewController {
    @Autowired
    PerformanceReviewService performanceReviewService;

    @PostMapping("/reviews")
    public ResponseEntity<PerformanceReviewResponse> submitReview(@RequestBody PerformanceReviewRequest reviewRequest) {
        PerformanceReviewResponse reviewResponse = performanceReviewService.submitReview(reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponse);

    }
}
