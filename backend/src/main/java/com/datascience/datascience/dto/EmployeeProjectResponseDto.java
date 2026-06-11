package com.datascience.datascience.dto;

import com.datascience.datascience.enums.ProjectRole;

public record EmployeeProjectResponseDto(
        Long id,
        Long employeeId,
        String employeeName,
        Long projectId,
        String projectName,
        ProjectRole role
) {
}
