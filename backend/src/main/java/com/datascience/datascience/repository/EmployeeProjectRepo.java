package com.datascience.datascience.repository;

import com.datascience.datascience.entity.EmployeeProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeProjectRepo extends JpaRepository<EmployeeProject, Long> {
    List<EmployeeProject> findByEmployeeId(Long employeeId);

    List<EmployeeProject> findByProjectId(Long projectId);

    boolean existsByEmployeeIdAndProjectId(Long employeeId, Long projectId);
}
