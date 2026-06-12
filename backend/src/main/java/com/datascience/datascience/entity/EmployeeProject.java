package com.datascience.datascience.entity;

import com.datascience.datascience.enums.ProjectRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(
        name="employee_projects",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "employee_id",
                                 "project_id"
                        }
                )
        }
)
public class EmployeeProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectRole role;
}
