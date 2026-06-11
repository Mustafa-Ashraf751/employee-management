package com.datascience.datascience.dto;

import java.time.LocalDate;

public record ProjectResponseDto(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        Long departmentId,
        String departmentName
) {
}
