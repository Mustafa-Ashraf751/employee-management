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

import java.util.List;

@RestController
@RequestMapping("/api/employee-project")
public class EmployeeProjectController {
    private final EmployeeProjectService employeeProjectService;

    public EmployeeProjectController(EmployeeProjectService employeeProjectService) {
        this.employeeProjectService = employeeProjectService;
    }

    @PostMapping
    public ResponseEntity<EmployeeProjectResponseDto> assignEmployeeToProject(@Valid @RequestBody EmployeeProjectRequestDto employeeProjectRequestDto) {
        return new ResponseEntity<>(employeeProjectService.assignEmployeeToProject(employeeProjectRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeEmployeeFromProject(@PathVariable Long id) {
        return new ResponseEntity<>(employeeProjectService.removeEmployeeFromProject(id), HttpStatus.OK);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<List<ProjectResponseDto>> getEmployeeProjectById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeProjectService.getProjectsByEmployeeId(id), HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeProjectService.getEmployeesByProjectId(id), HttpStatus.OK);
    }

}
