package com.datascience.datascience.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DepartmentResponseDto(
        @NotNull(message = "ID is required")
        Long id,

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Location is required")
        String location,

        @Positive(message = "Salary must be positive")
        Double budget
)
{ }
