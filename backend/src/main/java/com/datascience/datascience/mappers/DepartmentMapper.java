package com.datascience.datascience.mappers;

import com.datascience.datascience.Dto.DepartmentRequestDto;
import com.datascience.datascience.Dto.DepartmentResponseDto;
import com.datascience.datascience.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department toEntity(
            DepartmentRequestDto dto);

    DepartmentResponseDto toDto(
            Department department);
}
