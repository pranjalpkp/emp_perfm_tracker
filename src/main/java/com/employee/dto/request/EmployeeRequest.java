package com.employee.dto.request;

import java.time.LocalDate;
import java.util.List;

public record EmployeeRequest(
    String empName,
    Integer deptId,
    List<Long> roleIds,
    LocalDate joiningDate
) {}
