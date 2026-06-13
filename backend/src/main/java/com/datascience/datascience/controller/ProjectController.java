package com.datascience.datascience.controller;

import com.datascience.datascience.dto.ProjectRequestDto;
import com.datascience.datascience.dto.ProjectResponseDto;
import com.datascience.datascience.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "Project", description = "Operations pertaining to project management")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Create a new project", description = "Adds a new project to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Department not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(@Valid @RequestBody ProjectRequestDto projectRequestDto) {
        ProjectResponseDto newProject = this.projectService.createProject(projectRequestDto);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all projects", description = "Retrieves a list of all projects.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getProjects() {
        List<ProjectResponseDto> projects = this.projectService.getProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @Operation(summary = "Get projects by department ID", description = "Retrieves a list of projects belonging to a specific department.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<ProjectResponseDto>> getProjectsByDepartmentId(@PathVariable Long departmentId) {
        List<ProjectResponseDto> projects = this.projectService.getProjectsByDepartmentId(departmentId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @Operation(summary = "Get a project by ID", description = "Retrieves a specific project by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> getProject(@PathVariable Long id) {
        ProjectResponseDto project = this.projectService.getProject(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @Operation(summary = "Update an existing project", description = "Updates the details of an existing project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Project or Department not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequestDto projectRequestDto) {
        ProjectResponseDto updatedProject = this.projectService.updateProject(id, projectRequestDto);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @Operation(summary = "Delete a project", description = "Removes a project from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        return new ResponseEntity<>(this.projectService.deleteProject(id), HttpStatus.OK);
    }
}
