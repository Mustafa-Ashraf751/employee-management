import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'departments',
    loadComponent: () => import('./pages/department-list/department-list.component').then(c => c.DepartmentList)
  },
  {
    path: 'employees',
    loadComponent: () => import('./pages/employee-list/employee-list.component').then(c => c.EmployeeList)
  },
  {
    path: 'projects',
    loadComponent: () => import('./pages/project-list/project-list.component').then(c => c.ProjectList)
  },
  {
    path: 'assignments',
    loadComponent: () => import('./pages/assignment-list/assignment-list.component').then(c => c.AssignmentList)
  },
  {
    path: '',
    redirectTo: 'departments',
    pathMatch: 'full'
  }
];
