package com.datascience.datascience.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DepartmentRequestDto(
        @NotBlank(message = "Department's name is required")
        @Size(min = 3,max = 20,message = "The department name must be between 3 and 20 characters")
        String name,

        @NotBlank(message = "Department's location is required")
        @Size(min = 3,max = 20,message = "The department location must be between 3 and 20 characters")
        String location,

        @Positive (message = "Department's budget must be positive")
        Double budget)
{ }
