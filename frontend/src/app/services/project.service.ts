import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { Project } from '../models/project';

@Injectable({
  	providedIn: 'root',
})
export class ProjectService {

    private apiUrl = `${environment.apiUrl}/projects`;

    constructor(private http: HttpClient) {}

    getProjects(): Observable<Project[]> {
        return this.http.get<Project[]>(this.apiUrl);
    }

    addProject(project: Project): Observable<Project> {
        return this.http.post<Project>(this.apiUrl, project);
    }

    updateProject(project : Project): Observable<Project> {
        return this.http.put<Project>(`${this.apiUrl}/${project.id}`, project);
    }

    deleteProject(id: number): Observable<string> {
      return this.http.delete<string>(`${this.apiUrl}/${id}`, { responseType: 'text' as 'json' });
    }

}
