import { Component, OnInit, signal } from '@angular/core';
import { AssignmentService } from '../../services/assignment.service';
import { EmployeeService } from '../../services/employees.service';
import { ProjectService } from '../../services/project.service';
import { DataTable } from '../../shared/data-table/data-table.component';
import { FormInput } from '../../shared/form-input/form-input.component';
import { FormField } from '../../models/formField';
import { ToastrService } from 'ngx-toastr';
import { Assignment } from '../../models/assignment';
import { RoleLabelPipe } from '../../shared/pipes/role-label.pipe';

@Component({
  selector: 'app-assignment-list',
  imports: [DataTable, FormInput],
  templateUrl: './assignment-list.html',
  styleUrl: './assignment-list.scss',
  providers: [RoleLabelPipe],
})
export class AssignmentList implements OnInit {
  columns = signal<string[]>(['employeeName', 'projectName', 'role']);
  tableData = signal<any[]>([]);
  showActions = signal<boolean>(true);
  formFields: FormField[] = [];
  isFormVisible = signal<boolean>(false);

  // Filters
  employeesList = signal<any[]>([]);
  projectsList = signal<any[]>([]);
  selectedEmployeeFilter = signal<number | null>(null);
  selectedProjectFilter = signal<number | null>(null);

  constructor(
    private assignmentService: AssignmentService,
    private employeeService: EmployeeService,
    private projectService: ProjectService,
    private toastr: ToastrService,
    private roleLabelPipe: RoleLabelPipe
  ) {}

  ngOnInit(): void {
    this.loadAssignments();
    this.loadFormOptions();
  }

  loadAssignments(): void {
    this.selectedEmployeeFilter.set(null);
    this.selectedProjectFilter.set(null);
    this.showActions.set(true);
    this.assignmentService.getAssignments().subscribe((data) => {
      this.columns.set(['employeeName', 'projectName', 'role']);
      this.tableData.set(
        data.map((a) => ({ ...a, role: this.roleLabelPipe.transform(a.role) }))
      );
    });
  }

  loadFormOptions(): void {
    this.employeeService.getEmployees().subscribe((employees) => {
      this.employeesList.set(employees);
      this.projectService.getProjects().subscribe((projects) => {
        this.projectsList.set(projects);
        this.formFields = [
          {
            name: 'EmployeeId',
            label: 'Employee',
            type: 'select',
            required: true,
            options: employees.map((e) => ({ label: e.name, value: e.id })),
          },
          {
            name: 'projectId',
            label: 'Project',
            type: 'select',
            required: true,
            options: projects.map((p) => ({ label: p.name, value: p.id })),
          },
          {
            name: 'role',
            label: 'Role',
            type: 'select',
            required: true,
            options: [
              { label: 'Backend Developer',    value: 'BACKEND_DEVELOPER' },
              { label: 'Frontend Developer',   value: 'FRONTEND_DEVELOPER' },
              { label: 'Project Manager',      value: 'PROJECT_MANAGER' },
              { label: 'Business Analyst',     value: 'BUSINESS_ANALYST' },
              { label: 'Technical Lead',       value: 'TECHNICAL_LEAD' },
              { label: 'HR Specialist',        value: 'HR_SPECIALIST' },
              { label: 'Technical Consultant', value: 'TECHNICAL_CONSULTANT' },
            ],
          },
        ];
      });
    });
  }

  openForm(): void {
    this.isFormVisible.set(true);
  }

  closeForm(): void {
    this.isFormVisible.set(false);
  }

  onEmployeeFilterChange(event: any): void {
    const value = event.target.value;
    if (value) {
      this.selectedProjectFilter.set(null); // Clear other filter
      this.selectedEmployeeFilter.set(Number(value));
      this.showActions.set(false);
      this.assignmentService.getProjectsByEmployee(Number(value)).subscribe((projects) => {
        this.columns.set(['name', 'description', 'startDate', 'endDate']);
        this.tableData.set(projects);
      });
    } else {
      this.loadAssignments();
    }
  }

  onProjectFilterChange(event: any): void {
    const value = event.target.value;
    if (value) {
      this.selectedEmployeeFilter.set(null); // Clear other filter
      this.selectedProjectFilter.set(Number(value));
      this.showActions.set(false);
      this.assignmentService.getEmployeesByProject(Number(value)).subscribe((employees) => {
        this.columns.set(['name', 'email', 'phone', 'salary']);
        this.tableData.set(employees);
      });
    } else {
      this.loadAssignments();
    }
  }

  onFormSubmit(formData: any): void {
    this.assignmentService.assignEmployee(formData).subscribe({
      next: () => {
        this.loadAssignments();
        this.closeForm();
        this.toastr.success('Assignment added successfully!');
      },
      error: () => {
        this.toastr.error('Failed to add assignment. Please try again.');
      },
    });
  }

  onDelete(assignment: Assignment): void {
    if (confirm(`Are you sure you want to remove this assignment?`)) {
      this.assignmentService.removeAssignment(assignment.id).subscribe({
        next: () => {
          this.loadAssignments();
          this.toastr.success('Assignment removed successfully!');
        },
        error: () => {
          this.toastr.error('Failed to remove assignment. Please try again.');
        },
      });
    }
  }
}
