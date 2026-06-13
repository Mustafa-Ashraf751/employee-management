package com.datascience.datascience.controller;

import com.datascience.datascience.dto.EmployeeProjectRequestDto;
import com.datascience.datascience.dto.EmployeeProjectResponseDto;
import com.datascience.datascience.dto.EmployeeResponseDto;
import com.datascience.datascience.dto.ProjectResponseDto;
import com.datascience.datascience.service.EmployeeProjectService;
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
@RequestMapping("/api/employee-project")
@Tag(name = "EmployeeProject Assignment", description = "Operations pertaining to employee and project assignments")
public class EmployeeProjectController {
    private final EmployeeProjectService employeeProjectService;

    public EmployeeProjectController(EmployeeProjectService employeeProjectService) {
        this.employeeProjectService = employeeProjectService;
    }

    @Operation(summary = "Get all assignments", description = "Retrieves a list of all employee-project assignments.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignments retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<EmployeeProjectResponseDto>> getAll() {
        return new ResponseEntity<>(employeeProjectService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Assign employee to project", description = "Creates a new assignment associating an employee with a project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee assigned to project successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or duplicate assignment"),
            @ApiResponse(responseCode = "404", description = "Employee or Project not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<EmployeeProjectResponseDto> assignEmployeeToProject(@Valid @RequestBody EmployeeProjectRequestDto employeeProjectRequestDto) {
        return new ResponseEntity<>(employeeProjectService.assignEmployeeToProject(employeeProjectRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Remove employee from project", description = "Deletes an existing assignment by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignment removed successfully"),
            @ApiResponse(responseCode = "404", description = "Assignment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeEmployeeFromProject(@PathVariable Long id) {
        return new ResponseEntity<>(employeeProjectService.removeEmployeeFromProject(id), HttpStatus.OK);
    }

    @Operation(summary = "Get projects by employee", description = "Retrieves all projects assigned to a specific employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/projects/{id}")
    public ResponseEntity<List<ProjectResponseDto>> getEmployeeProjectById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeProjectService.getProjectsByEmployeeId(id), HttpStatus.OK);
    }

    @Operation(summary = "Get employees by project", description = "Retrieves all employees assigned to a specific project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/employees/{id}")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeProjectService.getEmployeesByProjectId(id), HttpStatus.OK);
    }

}
