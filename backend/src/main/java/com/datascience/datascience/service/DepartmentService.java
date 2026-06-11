package com.datascience.datascience.service;

import com.datascience.datascience.dto.DepartmentRequestDto;
import com.datascience.datascience.dto.DepartmentResponseDto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface DepartmentService {

    List<DepartmentResponseDto> getDepartments();
    DepartmentResponseDto getDepartmentById(Long id);
    DepartmentResponseDto createDepartment(DepartmentRequestDto departmentDto);
    DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto departmentDto);
    String deleteDepartment(Long id);

}
