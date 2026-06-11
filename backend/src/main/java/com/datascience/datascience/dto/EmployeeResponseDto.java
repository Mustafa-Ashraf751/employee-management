package com.datascience.datascience.dto;


import java.time.LocalDate;

public record EmployeeResponseDto(
        Long id,
        String name,
        String email,
        String phone,
        LocalDate hireDate,
        Double salary,
        Long departmentId,
        String departmentName
) {
}
