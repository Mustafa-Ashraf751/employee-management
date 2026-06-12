import { Component, OnInit, signal } from '@angular/core';
import { Project } from '../../models/project';
import { FormField } from '../../models/formField';
import { ProjectService } from '../../services/project.service';
import { FormInput } from '../../shared/form-input/form-input.component';
import { DataTable } from '../../shared/data-table/data-table.component';

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
    'DepartmentName'
  ];
  projects = signal<Project[]>([]);
  formFields: FormField[] = [
    { name: 'name', label: 'Name', type: 'text', required: true },
    { name: 'description', label: 'Description', type: 'text', required: true },
    { name: 'startDate', label: 'Start Date', type: 'date', required: true },
    { name: 'endDate', label: 'End Date', type: 'date', required: true },
    { name: 'departmentId', label: 'Department', type: 'select', required: true }
  ]
  isFormVisible = signal<boolean>(false);
  selectedProject: Project | null = null;

  constructor(private projectService: ProjectService) {}

  ngOnInit(): void {
      this.loadProjects();
  }

  loadProjects(): void {
    this.projectService.getProjects().subscribe((projects) => {
      this.projects.set(projects);
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
            });
          } else {
            // Add new project
            this.projectService.addProject(formData).subscribe(() => {
              this.loadProjects();
              this.closeForm();
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
      });
    }
  }

}
