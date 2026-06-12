package com.datascience.datascience.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ProjectRequestDto(
        @NotBlank(message = "Project's name is required")
        @Size(min = 3, max = 50,message = "Project's name must be between 3 and 50 characters")
        String name,
        @NotBlank(message = "Project's description is required")
        @Size(min = 3, max = 50,message = "Project's description must be between 3 and 50 characters")
        String description,
        @NotNull(message = "Project's start date is required")
        @PastOrPresent(message = "Project's start date cannot be in the future")
        LocalDate startDate,
        @NotNull(message = "Project's end date is required")
        @FutureOrPresent(message = "Project's end date cannot be in the past")
        LocalDate endDate,
        @NotNull(message = "Department ID is required")
        Long departmentId
) {
}
