package com.datascience.datascience.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record DepartmentRequestDto(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Location is required")
        String location,

        @Positive (message = "Salary must be positive")
        Double budget)
{ }
