package com.employee.dto.request;

import java.time.LocalDateTime;

public record PerformanceReviewRequest(
    Long empId,
    Long reviewCycleId,
    double rating,
    String reviewerNotes,
    LocalDateTime submittedAt
) {}
