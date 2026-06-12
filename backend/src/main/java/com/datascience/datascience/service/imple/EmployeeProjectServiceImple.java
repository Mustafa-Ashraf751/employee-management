package com.datascience.datascience.service.imple;

import com.datascience.datascience.dto.EmployeeProjectRequestDto;
import com.datascience.datascience.dto.EmployeeProjectResponseDto;
import com.datascience.datascience.dto.EmployeeResponseDto;
import com.datascience.datascience.dto.ProjectResponseDto;
import com.datascience.datascience.entity.Employee;
import com.datascience.datascience.entity.EmployeeProject;
import com.datascience.datascience.entity.Project;
import com.datascience.datascience.exception.ResourceNotFoundException;
import com.datascience.datascience.mappers.EmployeeMapper;
import com.datascience.datascience.mappers.EmployeeProjectMapper;
import com.datascience.datascience.mappers.ProjectMapper;
import com.datascience.datascience.repository.EmployeeProjectRepo;
import com.datascience.datascience.repository.EmployeeRepo;
import com.datascience.datascience.repository.ProjectRepo;
import com.datascience.datascience.service.EmployeeProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeProjectServiceImple implements EmployeeProjectService {
    private final EmployeeProjectRepo employeeProjectRepo;
    private final EmployeeProjectMapper employeeProjectMapper;
    private final EmployeeRepo employeeRepo;
    private final ProjectRepo projectRepo;
    private final ProjectMapper projectMapper;
    private final EmployeeMapper employeeMapper;

    public EmployeeProjectServiceImple(EmployeeProjectRepo employeeProjectRepo, EmployeeProjectMapper employeeProjectMapper, EmployeeRepo employeeRepo, ProjectRepo projectRepo, ProjectMapper projectMapper, EmployeeMapper employeeMapper) {
        this.employeeProjectRepo = employeeProjectRepo;
        this.employeeProjectMapper = employeeProjectMapper;
        this.employeeRepo = employeeRepo;
        this.projectRepo = projectRepo;
        this.projectMapper = projectMapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<EmployeeProjectResponseDto> getAll() {
        return employeeProjectRepo.findAll().stream()
                .map(employeeProjectMapper::toEmployeeResponseDto)
                .toList();
    }

    @Override
    public EmployeeProjectResponseDto assignEmployeeToProject(EmployeeProjectRequestDto employeeProjectRequestDto) {
        Employee employee = employeeRepo.findById(employeeProjectRequestDto.EmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        Project project = projectRepo.findById(employeeProjectRequestDto.projectId())
                .orElseThrow(()-> new ResourceNotFoundException("Project not found"));
        EmployeeProject employeeProject = EmployeeProject.builder()
                .employee(employee)
                .project(project)
                .role(employeeProjectRequestDto.role())
                .build();
        employeeProjectRepo.save(employeeProject);
        return employeeProjectMapper.toEmployeeResponseDto(employeeProject);
    }

    @Override
    public String removeEmployeeFromProject(Long employeeProjectId) {
         EmployeeProject employeeProject = employeeProjectRepo.findById(employeeProjectId)
                 .orElseThrow(()-> new ResourceNotFoundException("Assignment not found"));
         employeeProjectRepo.delete(employeeProject);
         return "Assignment has been removed";
    }

    @Override
    public List<ProjectResponseDto> getProjectsByEmployeeId(Long employeeId) {
        List<EmployeeProject> employeeProjects = employeeProjectRepo.findByEmployeeId(employeeId);
        return employeeProjects.stream()
                .map(EmployeeProject::getProject)
                .map(projectMapper::projectToProjectResponseDto)
                .toList();
    }

    @Override
    public List<EmployeeResponseDto> getEmployeesByProjectId(Long projectId) {
        List<EmployeeProject> employeeProjects = employeeProjectRepo.findByProjectId(projectId);
        return employeeProjects.stream()
                .map(EmployeeProject::getEmployee)
                .map(employeeMapper::toEmployeeResponseDto)
                .toList();
    }
}
