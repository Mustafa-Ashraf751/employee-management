package com.datascience.datascience.repository;

import com.datascience.datascience.entity.Department;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
    boolean existsByNameIgnoreCase(@NotBlank(message = "Name is required") String name);
}
