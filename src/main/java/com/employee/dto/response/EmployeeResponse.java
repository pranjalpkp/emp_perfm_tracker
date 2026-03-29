package com.employee.dto.response;

import java.time.LocalDate;
import java.util.List;

public record EmployeeResponse(
    Long empId,
    String empName,
    String deptName,
    List<String> roleNames,
    LocalDate joiningDate
) {
    
}
