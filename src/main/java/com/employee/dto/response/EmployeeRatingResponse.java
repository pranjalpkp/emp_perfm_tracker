package com.employee.dto.response;

public record EmployeeRatingResponse(
        Long empId,
        String empName,
        String department,
        Double averageRating) {

}
