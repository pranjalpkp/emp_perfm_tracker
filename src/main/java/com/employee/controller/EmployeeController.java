package com.employee.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.employee.dto.request.EmployeeRequest;
import com.employee.dto.response.EmployeeResponse;
import com.employee.service.EmployeeService;

@RestController
public class EmployeeController {

    @Autowired EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest emp) {
        EmployeeResponse employee = employeeService.createEmployee(emp);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }
}
