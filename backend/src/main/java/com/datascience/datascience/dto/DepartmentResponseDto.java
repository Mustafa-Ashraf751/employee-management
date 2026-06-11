package com.datascience.datascience.dto;


public record DepartmentResponseDto(
        Long id,
        String name,
        String location,
        Double budget
)
{ }
