import { Component, OnInit,signal } from '@angular/core';
import { Employee } from '../../models/employees';
import { EmployeeService } from '../../services/employees.service';
import { FormInput } from '../../shared/form-input/form-input.component';
import { DataTable } from '../../shared/data-table/data-table.component';
import { ConfirmDialogComponent } from '../../shared/confirm-dialog/confirm-dialog.component';
import { ToastrService } from 'ngx-toastr';
import { DepartmentService } from '../../services/department.service';
import { FormField } from '../../models/formField';

@Component({
  selector: 'app-employee-list',
  imports: [DataTable, FormInput, ConfirmDialogComponent],
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
  selectedDepartmentFilter = signal<number | null>(null);

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

  onDepartmentFilterChange(event: any): void {
    const value = event.target.value;
    if (value) {
      this.selectedDepartmentFilter.set(Number(value));
      this.employeeService.getEmployeesByDepartment(Number(value)).subscribe((employees) => {
        this.employees.set(employees);
      });
    } else {
      this.selectedDepartmentFilter.set(null);
      this.loadEmployees();
    }
  }

  onFormSubmit(formData: any): void {
      if (this.selectedEmployee) {
        // Update existing employee
        const updatedEmployee: Employee = { ...formData, id: this.selectedEmployee.id };
        this.employeeService.updateEmployee(updatedEmployee).subscribe(
          {
            next: (res: any) => {
              this.loadEmployees();
              this.closeForm();
              const msg = typeof res === 'string' ? res : 'Employee updated successfully!';
              this.toastr.success(msg);
            },
            error: (err) => {
              let msg = 'Failed to update employee. Please try again.';
              if (err.error) {
                  if (typeof err.error === 'string') {
                      try { msg = JSON.parse(err.error).message || err.error; } catch { msg = err.error; }
                  } else { msg = err.error.message || msg; }
              }
              this.toastr.error(msg);
            }
          }
        );
      } else {
        // Add new employee
        this.employeeService.addEmployee(formData).subscribe(
          {
            next: (res: any) => {
              this.loadEmployees();
              this.closeForm();
              const msg = typeof res === 'string' ? res : 'Employee added successfully!';
              this.toastr.success(msg);
            },
            error: (err) => {
              let msg = 'Failed to add employee. Please try again.';
              if (err.error) {
                  if (typeof err.error === 'string') {
                      try { msg = JSON.parse(err.error).message || err.error; } catch { msg = err.error; }
                  } else { msg = err.error.message || msg; }
              }
              this.toastr.error(msg);
            }
          }
        );
      }
  }

  itemToDelete = signal<Employee | null>(null);

  onEdit(employee: Employee): void {
    this.openForm(employee);
  }

  onDelete(employee: Employee): void {
    this.itemToDelete.set(employee);
  }

  confirmDelete(): void {
    const employee = this.itemToDelete();
    if (employee) {
      this.employeeService.deleteEmployee(employee.id).subscribe({
        next: (res: any) => {
          this.loadEmployees();
          this.itemToDelete.set(null);
          const msg = typeof res === 'string' ? res : 'Employee deleted successfully!';
          this.toastr.success(msg);
        },
        error: (err) => {
          this.itemToDelete.set(null);
          let msg = 'Failed to delete employee. Please try again.';
          if (err.error) {
              if (typeof err.error === 'string') {
                  try { msg = JSON.parse(err.error).message || err.error; } catch { msg = err.error; }
              } else { msg = err.error.message || msg; }
          }
          this.toastr.error(msg);
        }
      });
    }
  }

  cancelDelete(): void {
    this.itemToDelete.set(null);
  }

}
