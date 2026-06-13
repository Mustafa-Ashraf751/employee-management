package com.datascience.datascience.repository;

import com.datascience.datascience.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    List<Project> findByDepartmentId(Long departmentId);
}
