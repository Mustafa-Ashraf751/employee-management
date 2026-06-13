package com.datascience.datascience.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EmployeeRequestDto(
        @NotBlank(message = "Employee name is required")
        @Size(min = 3,max = 50,message = "The employee name must be between 3 and 50 characters")
        String name,
        @Email(message="Please provide a valid email")
        @NotBlank(message="Email is required")
        String email,
        @NotBlank(message="Phone number is required")
        @Pattern(
                regexp = "^\\+?[0-9]{10,15}$",
                message = "Invalid phone number format"
        )
        String phone,
        @NotNull(message="Hiring date is required")
        @PastOrPresent(message = "Hire date cannot be in the future")
        LocalDate hireDate,
        @Positive(message = "salary should be positive number")
        Double salary,
        @NotNull(message = "Department ID is required")
        Long departmentId
) {
}
