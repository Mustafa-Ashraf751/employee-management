package com.datascience.datascience.controller;

import com.datascience.datascience.dto.ProjectRequestDto;
import com.datascience.datascience.dto.ProjectResponseDto;
import com.datascience.datascience.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(@Valid @RequestBody ProjectRequestDto projectRequestDto) {
        ProjectResponseDto newProject = this.projectService.createProject(projectRequestDto);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getProjects() {
        List<ProjectResponseDto> projects = this.projectService.getProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> getProject(@PathVariable Long id) {
        ProjectResponseDto project = this.projectService.getProject(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequestDto projectRequestDto) {
        ProjectResponseDto updatedProject = this.projectService.updateProject(id, projectRequestDto);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        return new ResponseEntity<>(this.projectService.deleteProject(id), HttpStatus.OK);
    }

    @PutMapping("/{projectId}/departments/{departmentId}")
    public ResponseEntity<ProjectResponseDto> updateProjectDepartment(@PathVariable Long projectId, @PathVariable Long departmentId) {
        return new ResponseEntity<>(this.projectService.assignDepartment(projectId, departmentId), HttpStatus.OK);
    }
}
