import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { Assignment, AssignmentRequest } from '../models/assignment';

@Injectable({
  providedIn: 'root',
})
export class AssignmentService {
  private apiUrl = `${environment.apiUrl}/employee-project`;

  constructor(private http: HttpClient) {}

  getAssignments(): Observable<Assignment[]> {
    return this.http.get<Assignment[]>(this.apiUrl);
  }

  assignEmployee(request: AssignmentRequest): Observable<Assignment> {
    return this.http.post<Assignment>(this.apiUrl, request);
  }

  removeAssignment(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
  }

  getProjectsByEmployee(employeeId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/projects/${employeeId}`);
  }

  getEmployeesByProject(projectId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/employees/${projectId}`);
  }
}
