package com.employee.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.employee.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    @Query(value = """
            SELECT
            e.emp_id,
            e.emp_name,
            d.dept_name,
            AVG(r.rating) as avg_rating
            FROM employees_tbl e
            JOIN departments_tbl d ON e.dept_id = d.dept_id
            JOIN performance_reviews_tbl r ON r.emp_id = e.emp_id
            WHERE d.dept_name = :department
            GROUP BY e.emp_id, e.emp_name, d.dept_name
            HAVING AVG(r.rating) >= :minRating
            """, nativeQuery = true)
    List<Object[]> findEmployeesByDepartmentAndMinRating(
            String department,
            Double minRating);

}
