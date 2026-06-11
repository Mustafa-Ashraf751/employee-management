package com.datascience.datascience.mappers;

import com.datascience.datascience.dto.DepartmentRequestDto;
import com.datascience.datascience.dto.DepartmentResponseDto;
import com.datascience.datascience.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department toEntity(
            DepartmentRequestDto dto);

    DepartmentResponseDto toDto(
            Department department);
}
