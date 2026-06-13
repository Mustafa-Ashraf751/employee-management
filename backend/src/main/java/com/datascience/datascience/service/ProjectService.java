package com.datascience.datascience.service;

import com.datascience.datascience.dto.ProjectRequestDto;
import com.datascience.datascience.dto.ProjectResponseDto;

import java.util.List;

public interface ProjectService {
    List<ProjectResponseDto> getProjects();
    List<ProjectResponseDto> getProjectsByDepartmentId(Long departmentId);
    ProjectResponseDto getProject(Long projectId);
    ProjectResponseDto createProject(ProjectRequestDto project);
    ProjectResponseDto updateProject(Long id,ProjectRequestDto project);
    String deleteProject(Long id);
}
