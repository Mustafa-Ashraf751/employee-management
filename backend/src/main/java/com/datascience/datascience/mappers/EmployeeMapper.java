package com.datascience.datascience.mappers;

import com.datascience.datascience.dto.EmployeeResponseDto;
import com.datascience.datascience.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "departmentName", source = "department.name")
    EmployeeResponseDto toEmployeeResponseDto(Employee employee);
}
