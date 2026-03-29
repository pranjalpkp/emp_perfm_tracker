package com.employee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.employee.dto.request.EmployeeRequest;
import com.employee.dto.response.EmployeeResponse;
import com.employee.model.Department;
import com.employee.model.Employee;
import com.employee.model.Role;
import com.employee.repo.DepartmentRepo;
import com.employee.repo.EmployeeRepo;
import com.employee.repo.RoleRepo;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final DepartmentRepo departmentRepo;
    private final RoleRepo roleRepo;

    public EmployeeService(EmployeeRepo employeeRepo, DepartmentRepo departmentRepo, RoleRepo roleRepo) {
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
        this.roleRepo = roleRepo;
    }

    public EmployeeResponse createEmployee(EmployeeRequest emp) {
        Department dept = departmentRepo.findById(emp.deptId())
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + emp.deptId()));

        List<Role> roles = roleRepo.findAllById(emp.roleIds());

        Employee employee = new Employee();
        employee.setEmpName(emp.empName());
        employee.setDepartment(dept);
        employee.setRoles(roles);
        employee.setJoiningDate(emp.joiningDate());
        employee = employeeRepo.save(employee);
        return new EmployeeResponse(
                employee.getEmpId(),
                employee.getEmpName(),
                employee.getDepartment().getDeptName(),
                employee.getRoles().stream().map(Role::getRoleName).toList(),
                employee.getJoiningDate());
    }
}
