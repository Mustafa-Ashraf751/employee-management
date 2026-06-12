package com.datascience.datascience.mappers;

import com.datascience.datascience.dto.EmployeeProjectResponseDto;
import com.datascience.datascience.entity.EmployeeProject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeProjectMapper {

    @Mapping(source = "employee.id",   target = "employeeId")
    @Mapping(source = "employee.name", target = "employeeName")
    @Mapping(source = "project.id",    target = "projectId")
    @Mapping(source = "project.name",  target = "projectName")
    EmployeeProjectResponseDto toEmployeeResponseDto(EmployeeProject employeeProject);
}
