import { Component, OnInit,signal } from '@angular/core';
import { Employee } from '../../models/employees';
import { EmployeeService } from '../../services/employees.service';
import { FormInput } from '../../shared/form-input/form-input.component';
import { DataTable } from '../../shared/data-table/data-table.component';

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
    'DepartmentName'
  ]
  employees = signal<Employee[]>([]);

  formFields = [
    { name: 'name', label: 'Name', type: 'text', required: true },
    { name: 'email', label: 'Email', type: 'email', required: true },
    { name: 'phone', label: 'Phone', type: 'text', required: true },
    { name: 'hireDate', label: 'Hire Date', type: 'date', required: true },
    { name: 'salary', label: 'Salary', type: 'number', required: true },
    { name: 'departmentName', label: 'Department', type: 'options', required: true }
  ];
  isFormVisible = signal<boolean>(false);
  selectedEmployee: Employee | null = null;

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {
      this.loadEmployees();
  }

  loadEmployees(): void {
    this.employeeService.getEmployees().subscribe((employees) => {
      this.employees.set(employees);
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
        this.employeeService.updateEmployee(updatedEmployee).subscribe(() => {
          this.loadEmployees();
          this.closeForm();
        });
      } else {
        // Add new employee
        this.employeeService.addEmployee(formData).subscribe(() => {
          this.loadEmployees();
          this.closeForm();
        });
      }
  }

  onEdit(employee: Employee): void {
    this.openForm(employee);
  }

  onDelete(employee: Employee): void {
    if (confirm(`Are you sure you want to delete ${employee.name}?`)) {
      this.employeeService.deleteEmployee(employee.id).subscribe(() => {
        this.loadEmployees();
      });
    }
  }

}
