package com.datascience.datascience.service.imple;

import com.datascience.datascience.dto.EmployeeRequestDto;
import com.datascience.datascience.dto.EmployeeResponseDto;
import com.datascience.datascience.entity.Department;
import com.datascience.datascience.entity.Employee;
import com.datascience.datascience.exception.ResourceNotFoundException;
import com.datascience.datascience.mappers.EmployeeMapper;
import com.datascience.datascience.repository.DepartmentRepo;
import com.datascience.datascience.repository.EmployeeRepo;
import com.datascience.datascience.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImple implements EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final EmployeeMapper employeeMapper;
    private final DepartmentRepo departmentRepo;

    public EmployeeServiceImple(EmployeeRepo employeeRepo1, EmployeeMapper employeeMapper1, DepartmentRepo departmentRepo) {
        this.employeeRepo = employeeRepo1;
        this.employeeMapper = employeeMapper1;
        this.departmentRepo = departmentRepo;
    }

    @Override
    public EmployeeResponseDto getEmployee(Long id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
        return employeeMapper.toEmployeeResponseDto(employee);
    }

    @Override
    public List<EmployeeResponseDto> getEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        return employees.stream().map(employeeMapper::toEmployeeResponseDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDto addEmployee(EmployeeRequestDto employeeRequestDto) {
        Employee newEmployee = Employee.builder()
                .name(employeeRequestDto.name())
                .email(employeeRequestDto.email())
                .phone(employeeRequestDto.phone())
                .hireDate(employeeRequestDto.hireDate())
                .salary(employeeRequestDto.salary())
                .build();
        employeeRepo.save(newEmployee);
        return employeeMapper.toEmployeeResponseDto(newEmployee);
    }

    @Override
    public EmployeeResponseDto updateEmployee(Long id,EmployeeRequestDto employeeRequestDto) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee with id " + id + " not found"));
        employee.setName(employeeRequestDto.name());
        employee.setEmail(employeeRequestDto.email());
        employee.setPhone(employeeRequestDto.phone());
        employee.setHireDate(employeeRequestDto.hireDate());
        employee.setSalary(employeeRequestDto.salary());
        Employee updatedEmployee = employeeRepo.save(employee);
        return employeeMapper.toEmployeeResponseDto(updatedEmployee);
    }

    @Override
    public String deleteEmployee(Long id) {
        Employee  employee = employeeRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee with id " + id + " not found"));
        employeeRepo.delete(employee);
        return "Employee with id " + id + " deleted";
    }

    @Override
    public EmployeeResponseDto assignDepartment(Long employeeId, Long departmentId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee with id " + employeeId + " not found"));
        Department department = departmentRepo.findById(departmentId)
                .orElseThrow(()-> new ResourceNotFoundException("Department with id " + departmentId + " not found"));
        employee.setDepartment(department);
        return employeeMapper.toEmployeeResponseDto(employeeRepo.save(employee));
    }

}
