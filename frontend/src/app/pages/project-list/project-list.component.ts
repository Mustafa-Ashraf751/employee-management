import { Component, OnInit, signal } from '@angular/core';
import { Project } from '../../models/project';
import { FormField } from '../../models/formField';
import { ProjectService } from '../../services/project.service';
import { FormInput } from '../../shared/form-input/form-input.component';
import { DataTable } from '../../shared/data-table/data-table.component';
import { ToastrService } from 'ngx-toastr';
import { DepartmentService } from '../../services/department.service';
import { ConfirmDialogComponent } from '../../shared/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-project-list',
  imports: [DataTable, FormInput, ConfirmDialogComponent],
  templateUrl: './project-list.html',
  styleUrl: './project-list.scss',
})
export class ProjectList implements OnInit {

  columns: string[] = [
    'name',
    'description',
    'startDate',
    'endDate',
    'departmentName'
  ];
  projects = signal<Project[]>([]);
  formFields: FormField[] = [
    { name: 'name', label: 'Name', type: 'text', required: true },
    { name: 'description', label: 'Description', type: 'text', required: true },
    { name: 'startDate', label: 'Start Date', type: 'date', required: true },
    { name: 'endDate', label: 'End Date', type: 'date' },
    { name: 'departmentId', label: 'Department', type: 'select', required: true, options: [] }
  ]
  isFormVisible = signal<boolean>(false);
  selectedProject: Project | null = null;

  departmentOptions = signal<{ label: string, value: number }[]>([]);
  selectedDepartmentFilter = signal<number | null>(null);

  constructor(private projectService: ProjectService, private toastr: ToastrService, private departmentService: DepartmentService) { }

  ngOnInit(): void {
    this.loadProjects();
    this.loadDepartments();
  }

  loadProjects(): void {
    this.projectService.getProjects().subscribe((projects) => {
      this.projects.set(projects);
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



  openForm(project: Project | null = null): void {
    this.selectedProject = project;
    this.isFormVisible.set(true);
  }

  closeForm(): void {
    this.selectedProject = null;
    this.isFormVisible.set(false);
  }

  onDepartmentFilterChange(event: any): void {
    const value = event.target.value;
    if (value) {
      this.selectedDepartmentFilter.set(Number(value));
      this.projectService.getProjectsByDepartment(Number(value)).subscribe((projects) => {
        this.projects.set(projects);
      });
    } else {
      this.selectedDepartmentFilter.set(null);
      this.loadProjects();
    }
  }

  onFormSubmit(formData: any): void {
          if (this.selectedProject) {
            // Update existing project
            const updatedProject: Project = { ...formData, id: this.selectedProject.id };
            this.projectService.updateProject(updatedProject).subscribe({
              next: (res: any) => {
                this.loadProjects();
                this.closeForm();
                const msg = typeof res === 'string' ? res : 'Project updated successfully!';
                this.toastr.success(msg);
              },
              error: (err) => {
                let msg = 'Failed to update project. Please try again.';
                if (err.error) {
                    if (typeof err.error === 'string') {
                        try { msg = JSON.parse(err.error).message || err.error; } catch { msg = err.error; }
                    } else { msg = err.error.message || msg; }
                }
                this.toastr.error(msg);
              }
            });
          } else {
            // Add new project
            this.projectService.addProject(formData).subscribe({
              next: (res: any) => {
                this.loadProjects();
                this.closeForm();
                const msg = typeof res === 'string' ? res : 'Project added successfully!';
                this.toastr.success(msg);
              },
              error: (err) => {
                let msg = 'Failed to add project. Please try again.';
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

  itemToDelete = signal<Project | null>(null);

  onEdit(project: Project): void {
    this.openForm(project);
  }

  onDelete(project: Project): void {
    this.itemToDelete.set(project);
  }

  confirmDelete(): void {
    const project = this.itemToDelete();
    if (project) {
      this.projectService.deleteProject(Number(project.id)).subscribe({
        next: (res: any) => {
          this.loadProjects();
          this.itemToDelete.set(null);
          const msg = typeof res === 'string' ? res : 'Project deleted successfully!';
          this.toastr.success(msg);
        },
        error: (err) => {
          this.itemToDelete.set(null);
          let msg = 'Failed to delete project. Please try again.';
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
