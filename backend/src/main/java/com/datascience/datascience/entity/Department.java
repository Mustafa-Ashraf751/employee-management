package com.datascience.datascience.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="departments")
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,unique=true)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double budget;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "department")
    private List<Project> projects = new ArrayList<>();

}
