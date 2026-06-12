package com.datascience.datascience.service;

import com.datascience.datascience.dto.EmployeeProjectRequestDto;
import com.datascience.datascience.dto.EmployeeProjectResponseDto;
import com.datascience.datascience.dto.EmployeeResponseDto;
import com.datascience.datascience.dto.ProjectResponseDto;

import java.util.List;

public interface EmployeeProjectService {
    List<EmployeeProjectResponseDto> getAll();

    EmployeeProjectResponseDto assignEmployeeToProject(EmployeeProjectRequestDto employeeProjectRequestDto);

    String removeEmployeeFromProject(Long employeeProjectId);

    List<ProjectResponseDto> getProjectsByEmployeeId(Long employeeId);

    List<EmployeeResponseDto> getEmployeesByProjectId(Long projectId);

}
