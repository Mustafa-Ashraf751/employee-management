import { Component, OnInit, signal } from '@angular/core';
import { Project } from '../../models/project';
import { FormField } from '../../models/formField';
import { ProjectService } from '../../services/project.service';
import { FormInput } from '../../shared/form-input/form-input.component';
import { DataTable } from '../../shared/data-table/data-table.component';
import { ToastrService } from 'ngx-toastr';
import { DepartmentService } from '../../services/department.service';

@Component({
  selector: 'app-project-list',
  imports: [DataTable, FormInput],
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
    { name: 'endDate', label: 'End Date', type: 'date', required: true },
    { name: 'departmentId', label: 'Department', type: 'select', required: true, options: [] }
  ]
  isFormVisible = signal<boolean>(false);
  selectedProject: Project | null = null;

  departmentOptions = signal<{ label: string, value: number }[]>([]);

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

  onFormSubmit(formData: any): void {
          if (this.selectedProject) {
            // Update existing project
            const updatedProject: Project = { ...formData, id: this.selectedProject.id };
            this.projectService.updateProject(updatedProject).subscribe(() => {
              this.loadProjects();
              this.closeForm();
              this.toastr.success('Project updated successfully!');
            }, () => {
              this.toastr.error('Failed to update project. Please try again.');
            });
          } else {
            // Add new project
            this.projectService.addProject(formData).subscribe(() => {
              this.loadProjects();
              this.closeForm();
              this.toastr.success('Project added successfully!');
            }, () => {
              this.toastr.error('Failed to add project. Please try again.');
            });
          }
  }

  onEdit(project: Project): void {
    this.openForm(project);
  }

  onDelete(project: Project): void {
    if (confirm(`Are you sure you want to delete the project "${project.name}"?`)) {
      this.projectService.deleteProject(Number(project.id)).subscribe(() => {
        this.loadProjects();
        this.toastr.success('Project deleted successfully!');
      }, () => {
        this.toastr.error('Failed to delete project. Please try again.');
      });
    }
  }

}
