import { Component, OnInit,signal } from '@angular/core';
import { Employee } from '../../models/employees';
import { EmployeeService } from '../../services/employees.service';
import { FormInput } from '../../shared/form-input/form-input.component';
import { DataTable } from '../../shared/data-table/data-table.component';
import { ToastrService } from 'ngx-toastr';
import { DepartmentService } from '../../services/department.service';
import { FormField } from '../../models/formField';

@Component({
  selector: 'app-employee-list',
  imports: [DataTable, FormInput],
  templateUrl: './employee-list.html',
  styleUrl: './employee-list.scss',
})
export class EmployeeList implements OnInit {
  columns: string[] = [
    'name',
    'email',
    'phone',
    'hireDate',
    'salary',
    'departmentName'
  ]
  employees = signal<Employee[]>([]);

  formFields: FormField[] = [
    { name: 'name', label: 'Name', type: 'text', required: true },
    { name: 'email', label: 'Email', type: 'email', required: true },
    { name: 'phone', label: 'Phone', type: 'text', required: true },
    { name: 'hireDate', label: 'Hire Date', type: 'date', required: true },
    { name: 'salary', label: 'Salary', type: 'number', required: true },
    { name: 'departmentId', label: 'Department', type: 'select', required: true, options: [] }
  ];
  isFormVisible = signal<boolean>(false);
  selectedEmployee: Employee | null = null;

  departmentOptions = signal<{ label: string, value: number }[]>([]);

  constructor(private employeeService: EmployeeService, private toastr: ToastrService, private departmentService: DepartmentService) { }

  ngOnInit(): void {
    this.loadEmployees();
    this.loadDepartments();
  }

  loadEmployees(): void {
    this.employeeService.getEmployees().subscribe((employees) => {
      this.employees.set(employees);
    });
  }

  loadDepartments(): void {
    this.departmentService.getDepartments().subscribe((departments) => {
      this.departmentOptions.set(
        departments.map((department) => ({
          label: department.name,
          value: department.id
        }))
      );

      this.formFields = this.formFields.map(field =>
        field.name === 'departmentId'
          ? { ...field, options: this.departmentOptions() }
          : field
      );
    });
  }

  openForm(employee: Employee | null = null): void {
    this.selectedEmployee = employee;
    this.isFormVisible.set(true);
  }

  closeForm(): void {
    this.selectedEmployee = null;
    this.isFormVisible.set(false);
  }

  onFormSubmit(formData: any): void {
      if (this.selectedEmployee) {
        // Update existing employee
        const updatedEmployee: Employee = { ...formData, id: this.selectedEmployee.id };
        this.employeeService.updateEmployee(updatedEmployee).subscribe(
          {
            next: () => {
              this.loadEmployees();
              this.closeForm();
              this.toastr.success('Employee updated successfully!');
            },
            error: () => {
              this.toastr.error('Failed to update employee. Please try again.');
            }
          }
        );
      } else {
        // Add new employee
        this.employeeService.addEmployee(formData).subscribe(
          {
            next: () => {
              this.loadEmployees();
              this.closeForm();
              this.toastr.success('Employee added successfully!');
            },
            error: () => {
              console.error('Error adding employee:');
              this.toastr.error('Failed to add employee. Please try again.');
            }
          }
        );
      }
  }

  onEdit(employee: Employee): void {
    this.openForm(employee);
  }

  onDelete(employee: Employee): void {
    if (confirm(`Are you sure you want to delete ${employee.name}?`)) {
      this.employeeService.deleteEmployee(employee.id).subscribe(() => {
        this.loadEmployees();
        this.toastr.success('Employee deleted successfully!');
      }, () => {
        this.toastr.error('Failed to delete employee. Please try again.');
      });
    }
  }

}
