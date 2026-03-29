package com.employee.dto.response;

import java.time.LocalDateTime;


public record PerformanceReviewResponse(
    Long reviewId,
        Long employeeId,
        String employeeName,

        Long cycleId,
        String cycleName,

        double rating,
        String reviewerNotes,
        LocalDateTime submittedAt
) {}