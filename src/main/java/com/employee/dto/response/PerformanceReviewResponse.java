package com.employee.dto.response;

import java.time.LocalDateTime;

public record PerformanceReviewResponse(
    Long reviewId,
    String empName,
    String reviewCycleName,
    double rating,
    String reviewerNotes,
    LocalDateTime submittedAt
) {}