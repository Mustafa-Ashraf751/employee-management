package com.datascience.datascience.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="departments")
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long departmentId;

    @Column(nullable = false,unique=true)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double budget;



}
