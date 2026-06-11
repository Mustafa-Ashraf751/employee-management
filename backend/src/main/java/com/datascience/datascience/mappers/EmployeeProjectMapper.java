package com.datascience.datascience.mappers;

import com.datascience.datascience.dto.EmployeeProjectResponseDto;
import com.datascience.datascience.dto.EmployeeResponseDto;
import com.datascience.datascience.entity.EmployeeProject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeProjectMapper {
    EmployeeProjectResponseDto toEmployeeResponseDto(EmployeeProject employeeProject);
    EmployeeProject toEmployeeProject(EmployeeResponseDto employeeResponseDto);
}
