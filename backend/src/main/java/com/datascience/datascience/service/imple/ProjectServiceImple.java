package com.datascience.datascience.service.imple;

import com.datascience.datascience.dto.ProjectRequestDto;
import com.datascience.datascience.dto.ProjectResponseDto;
import com.datascience.datascience.entity.Department;
import com.datascience.datascience.entity.Project;
import com.datascience.datascience.exception.ResourceNotFoundException;
import com.datascience.datascience.mappers.ProjectMapper;
import com.datascience.datascience.repository.DepartmentRepo;
import com.datascience.datascience.repository.ProjectRepo;
import com.datascience.datascience.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImple implements ProjectService {
    private final ProjectRepo projectRepo;
    private final ProjectMapper projectMapper;
    private final DepartmentRepo departmentRepo;

    public ProjectServiceImple(ProjectRepo projectRepo, ProjectMapper projectMapper, DepartmentRepo departmentRepo) {
        this.projectRepo = projectRepo;
        this.projectMapper = projectMapper;
        this.departmentRepo = departmentRepo;
    }

    @Override
    public List<ProjectResponseDto> getProjects() {
        List<Project> projects = projectRepo.findAll();
        return projects.stream().map(projectMapper::projectToProjectResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<ProjectResponseDto> getProjectsByDepartmentId(Long departmentId) {
        List<Project> projects = projectRepo.findByDepartmentId(departmentId);
        return projects.stream().map(projectMapper::projectToProjectResponseDto).collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDto getProject(Long projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(()-> new ResourceNotFoundException("Project with id " + projectId + " not found"));
        return projectMapper.projectToProjectResponseDto(project);
    }

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto project) {
        Project newProject = Project.builder()
                .name(project.name())
                .description(project.description())
                .startDate(project.startDate())
                .endDate(project.endDate())
                .department(departmentRepo.findById(project.departmentId())
                        .orElseThrow(() -> new ResourceNotFoundException("Department with id " + project.departmentId() + " not found")))
                .build();
        newProject = projectRepo.save(newProject);
        return projectMapper.projectToProjectResponseDto(newProject);
    }

    @Override
    public ProjectResponseDto updateProject(Long id, ProjectRequestDto project) {
        Project updatedProject = projectRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Project with id " + id + " not found"));
        updatedProject.setName(project.name());
        updatedProject.setDescription(project.description());
        updatedProject.setStartDate(project.startDate());
        updatedProject.setEndDate(project.endDate());
        updatedProject.setDepartment(departmentRepo.findById(project.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department with id " + project.departmentId() + " not found")));
        updatedProject = projectRepo.save(updatedProject);
        return projectMapper.projectToProjectResponseDto(updatedProject);
    }

    @Override
    public String deleteProject(Long id) {
        Project project = projectRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Project with id " + id + " not found"));
        projectRepo.delete(project);
        return "Project with id " + id + " deleted successfully";
    }
}
