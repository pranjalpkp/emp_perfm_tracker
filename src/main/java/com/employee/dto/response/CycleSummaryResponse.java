package com.employee.dto.response;

public record CycleSummaryResponse(
    Long cycleId,
    String cycleName,
    Double averageRating,
    String topPerformer,
    Long completedGoals,
    Long missedGoals
) {}
