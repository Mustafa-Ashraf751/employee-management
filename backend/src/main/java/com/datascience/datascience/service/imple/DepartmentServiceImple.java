package com.datascience.datascience.service.imple;

import com.datascience.datascience.dto.DepartmentRequestDto;
import com.datascience.datascience.dto.DepartmentResponseDto;
import com.datascience.datascience.entity.Department;
import com.datascience.datascience.entity.Employee;
import com.datascience.datascience.exception.BusinessException;
import com.datascience.datascience.exception.ResourceNotFoundException;
import com.datascience.datascience.mappers.DepartmentMapper;
import com.datascience.datascience.repository.DepartmentRepo;
import com.datascience.datascience.repository.EmployeeRepo;
import com.datascience.datascience.service.DepartmentService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImple implements DepartmentService {

    private final DepartmentRepo departmentRepo;
    private final DepartmentMapper departmentMapper;
    private final EmployeeRepo employeeRepo;

    public DepartmentServiceImple(DepartmentRepo departmentRepo, DepartmentMapper departmentMapper, EmployeeRepo employeeRepo) {
        this.departmentRepo = departmentRepo;
        this.departmentMapper = departmentMapper;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<DepartmentResponseDto> getDepartments() {
        List<Department> departments = departmentRepo.findAll();
        return departments.stream().map(departmentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public DepartmentResponseDto getDepartmentById(Long id) {
        Department department = departmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department with id " + id + " not found"));
        return departmentMapper.toDto(department);
    }

    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto departmentDto) {
        if (departmentRepo.existsByNameIgnoreCase(departmentDto.name())) {
            throw new BusinessException("Department with name " + departmentDto.name() + " already exists");
        }
        //Get the data from dto and save it to the database
        Department department = Department.builder()
                .name(departmentDto.name())
                .location(departmentDto.location())
                .budget(departmentDto.budget())
                .build();
        department = departmentRepo.save(department);
        return departmentMapper.toDto(department);
    }

    @Override
    public DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto departmentDto) {
        Department department = departmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department with id " + id + " not found"));
        department.setName(departmentDto.name());
        department.setLocation(departmentDto.location());
        department.setBudget(departmentDto.budget());

        Department updatedDepartment = departmentRepo.save(department);

        return departmentMapper.toDto(departmentRepo.save(updatedDepartment));
    }

    @Override
    public String deleteDepartment(Long id) {
        Department department = departmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department with id " + id + " not found"));
        //Check if department has employees first
        List<Employee> employees = employeeRepo.findByDepartmentId(id);
        if (!employees.isEmpty()) throw new BusinessException("Cannot delete department has employees");
        departmentRepo.delete(department);
        return "Department with id " + id + " deleted successfully";
    }

}
