package com.datascience.datascience.repository;

import com.datascience.datascience.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(Long id);
}
