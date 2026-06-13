package com.datascience.datascience.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectRequestDto(
        @NotBlank(message = "Project's name is required")
        @Size(min = 3, max = 50,message = "Project's name must be between 3 and 50 characters")
        String name,

        @NotBlank(message = "Project's description is required")
        @Size(min = 3, max = 50,message = "Project's description must be between 3 and 50 characters")
        String description,

        @NotNull(message = "Project's start date is required")
        LocalDate startDate,

        
        LocalDate endDate,

        @NotNull(message = "Department ID is required")
        Long departmentId
) {
}
