import { Component, OnInit, signal } from '@angular/core';
import { Department } from '../../models/department';
import { DepartmentService } from '../../services/department.service';
import { DataTable } from '../../shared/data-table/data-table.component';
import { FormField } from '../../models/formField';
import { FormInput } from '../../shared/form-input/form-input.component';
import { ConfirmDialogComponent } from '../../shared/confirm-dialog/confirm-dialog.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-department-list',
  imports: [DataTable, FormInput, ConfirmDialogComponent],
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
              next: (res: any) => {
                  this.loadDepartments();
                  this.closeForm();
                  const msg = typeof res === 'string' ? res : 'Department updated successfully!';
                  this.toastr.success(msg);
              },
              error: (err) => {
                  let msg = 'Failed to update department. Please try again.';
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
          // Add new department
          this.departmentService.addDepartment(formData).subscribe(
            {
              next: (res: any) => {
                this.loadDepartments();
                this.closeForm();
                const msg = typeof res === 'string' ? res : 'Department added successfully!';
                this.toastr.success(msg);
              },
              error: (err) => {
                  let msg = 'Failed to add department. Please try again.';
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

    itemToDelete = signal<Department | null>(null);

    onEdit(department: Department): void {
      this.openForm(department);
    }

    onDelete(department: Department): void {
      this.itemToDelete.set(department);
    }

    confirmDelete(): void {
      const department = this.itemToDelete();
      if (department) {
        this.departmentService.deleteDepartment(department.id).subscribe({
          next: (res: any) => {
            this.loadDepartments();
            this.itemToDelete.set(null);
            const msg = typeof res === 'string' ? res : 'Department deleted successfully!';
            this.toastr.success(msg);
          },
          error: (err) => {
            this.itemToDelete.set(null);
            let msg = 'Failed to delete department. Please try again.';
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
