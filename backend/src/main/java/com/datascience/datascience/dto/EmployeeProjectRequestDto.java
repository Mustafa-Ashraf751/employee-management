package com.datascience.datascience.dto;

import com.datascience.datascience.enums.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record EmployeeProjectRequestDto(
        @NotNull(message = "Employee id is required")
        Long EmployeeId,
        @NotNull(message = "Project id is required")
        Long projectId,
        @NotNull(message = "Role is required")
        ProjectRole role
) {
}
