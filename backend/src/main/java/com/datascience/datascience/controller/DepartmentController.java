package com.datascience.datascience.controller;

import com.datascience.datascience.dto.DepartmentRequestDto;
import com.datascience.datascience.dto.DepartmentResponseDto;
import com.datascience.datascience.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDto> createDepartment(@Valid @RequestBody DepartmentRequestDto departmentDto) {
        DepartmentResponseDto department = departmentService.createDepartment(departmentDto);
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> getAllDepartments() {
        List<DepartmentResponseDto> departments = departmentService.getDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable Long id) {
        return new ResponseEntity<>(departmentService.getDepartmentById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequestDto departmentDto) {
        DepartmentResponseDto department = departmentService.updateDepartment(id, departmentDto);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

//    @DeleteMapping("/id")
//    public ResponseEntity<DepartmentResponseDto> deleteDepartment(@PathVariable Long id) {
//        // Do it later
//        //return departmentService.deleteDepartment(id);
//    }
}
