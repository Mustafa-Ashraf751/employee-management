package com.datascience.datascience.service;

import com.datascience.datascience.dto.EmployeeRequestDto;
import com.datascience.datascience.dto.EmployeeResponseDto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface EmployeeService {
    EmployeeResponseDto getEmployee(Long id);
    List<EmployeeResponseDto> getEmployees();
    EmployeeResponseDto addEmployee(EmployeeRequestDto employeeRequestDto);
    EmployeeResponseDto updateEmployee(Long id,EmployeeRequestDto employeeRequestDto);
    String deleteEmployee(Long id);
    EmployeeResponseDto assignDepartment(Long employeeId, Long departmentId);
}
