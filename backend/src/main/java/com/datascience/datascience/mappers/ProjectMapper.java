package com.datascience.datascience.mappers;

import com.datascience.datascience.dto.ProjectResponseDto;
import com.datascience.datascience.entity.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectResponseDto projectToProjectResponseDto(Project project);
    Project projectResponseDtoToProject(ProjectResponseDto project);
}
