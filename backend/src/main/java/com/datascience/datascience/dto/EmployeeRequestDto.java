package com.datascience.datascience.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EmployeeRequestDto(
        @NotBlank(message = "Employee name is required")
        @Size(min = 3,max = 20,message = "The employee location must be between 3 and 20 characters")
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
        @NotBlank(message="Hiring date is required")
        @PastOrPresent(message = "Hire date cannot be in the future")
        LocalDate hireDate,
        @Positive(message = "salary should be positive number")
        Double salary
) {
}
