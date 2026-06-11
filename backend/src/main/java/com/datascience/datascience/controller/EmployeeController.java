package com.datascience.datascience.controller;

import com.datascience.datascience.dto.EmployeeRequestDto;
import com.datascience.datascience.dto.EmployeeResponseDto;
import com.datascience.datascience.entity.Employee;
import com.datascience.datascience.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> addEmployee(@Valid @RequestBody EmployeeRequestDto employee) {
         EmployeeResponseDto newEmployee = employeeService.addEmployee(employee);
         return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getEmployees() {
        List<EmployeeResponseDto> employees = employeeService.getEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployee(@PathVariable Long id) {
        EmployeeResponseDto employee = employeeService.getEmployee(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeRequestDto employee) {
        EmployeeResponseDto updatedEmployee = employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{employeeId}/department/{departmentId}")
    public ResponseEntity<EmployeeResponseDto> assignDepartment(@PathVariable Long employeeId, @PathVariable Long departmentId) {
        return new ResponseEntity<>(employeeService.assignDepartment(employeeId, departmentId), HttpStatus.OK);
    }
}
