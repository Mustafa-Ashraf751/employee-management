import { Component, OnInit, signal } from '@angular/core';
import { Department } from '../../models/department';
import { DepartmentService } from '../../services/department.service';
import { DataTable } from '../../shared/data-table/data-table.component';
import { FormField } from '../../models/formField';
import { FormInput } from '../../shared/form-input/form-input.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-department-list',
  imports: [DataTable, FormInput],
  templateUrl: './department-list.html',
  styleUrl: './department-list.scss',
})
export class DepartmentList implements OnInit {
    columns: string[] = [
      'name',
      'location',
      'budget'
    ];
    departments = signal<Department[]>([]);
    formFields: FormField[] = [
      { name: 'name', label: 'Name', type: 'text', required: true },
      { name: 'location', label: 'Location', type: 'text', required: true },
      { name: 'budget', label: 'Budget', type: 'number', required: true }
    ];
    isFormVisible = signal<boolean>(false);
    selectedDepartment: Department | null = null;

    constructor(private departmentService: DepartmentService, private toastr: ToastrService) {}

    ngOnInit(): void {
        this.loadDepartments();
    }

    loadDepartments(): void {
        this.departmentService.getDepartments().subscribe((departments) => {
          this.departments.set(departments);
        });
    }

    openForm(department: Department | null = null): void {
      this.selectedDepartment = department;
      this.isFormVisible.set(true);
    }

    closeForm(): void {
      this.selectedDepartment = null;
      this.isFormVisible.set(false);
    }

    onFormSubmit(formData: any): void {
        if (this.selectedDepartment) {
          // Update existing department
          const updatedDepartment: Department = { ...formData, id: this.selectedDepartment.id };
          this.departmentService.updateDepartment(updatedDepartment).subscribe(
            {
              next: ()=> {
                  this.loadDepartments();
                  this.closeForm();
                  this.toastr.success('Department updated successfully!');
              },error: () => {
                  this.toastr.error('Failed to update department. Please try again.');
              }
            }
          );
        } else {
          // Add new department
          this.departmentService.addDepartment(formData).subscribe(
            {
              next: () => {
                this.loadDepartments();
                this.closeForm();
                this.toastr.success('Department added successfully!');
              },
              error: () => {
                this.toastr.error('Failed to add department. Please try again.');
              }
            }
          );
        }
    }

    onEdit(department: Department): void {
      this.openForm(department);
    }

    onDelete(department: Department): void {
       if (confirm(`Are you sure you want to delete the department "${department.name}"?`)) {
         this.departmentService.deleteDepartment(department.id).subscribe(() => {
            this.loadDepartments();
            this.toastr.success('Department deleted successfully!');
         }, () => {
            this.toastr.error('Failed to delete department. Please try again.');
         });
       }
    }

}
