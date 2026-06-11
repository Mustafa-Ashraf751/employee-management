package com.datascience.datascience.mappers;

import com.datascience.datascience.dto.EmployeeRequestDto;
import com.datascience.datascience.dto.EmployeeResponseDto;
import com.datascience.datascience.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeResponseDto toEmployeeResponseDto(Employee employee);
    Employee toEmployee(EmployeeRequestDto employeeRequestDto);
}
