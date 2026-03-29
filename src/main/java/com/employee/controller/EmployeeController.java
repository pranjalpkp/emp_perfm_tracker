package com.employee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.employee.dto.request.EmployeeRequest;
import com.employee.dto.response.EmployeeRatingResponse;
import com.employee.dto.response.EmployeeResponse;
import com.employee.dto.response.PerformanceReviewResponse;
import com.employee.service.EmployeeService;
import com.employee.service.PerformanceReviewService;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PerformanceReviewService performanceReviewService;

    @PostMapping("/employees")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest emp) {
        EmployeeResponse employee = employeeService.createEmployee(emp);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @GetMapping("/employees/{id}/reviews")
    public ResponseEntity<List<PerformanceReviewResponse>> getEmployeeReviews(
            @PathVariable Long id) {

        List<PerformanceReviewResponse> reviews = performanceReviewService.getEmployeeReviews(id);
        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeRatingResponse>> getEmployees(
            @RequestParam String department,
            @RequestParam Double minRating) {

        List<EmployeeRatingResponse> employees = employeeService.filterEmployees(department, minRating);

        return ResponseEntity.ok(employees);
    }
}
