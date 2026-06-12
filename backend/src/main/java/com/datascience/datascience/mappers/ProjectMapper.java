package com.datascience.datascience.mappers;

import com.datascience.datascience.dto.ProjectResponseDto;
import com.datascience.datascience.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "departmentName", source = "department.name")
    ProjectResponseDto projectToProjectResponseDto(Project project);
}
