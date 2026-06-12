import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { Department } from '../models/department';

@Injectable({
  	providedIn: 'root',
})
export class DepartmentService {

    private apiUrl = `${environment.apiUrl}/departments`;

    constructor(private http: HttpClient) {}

    getDepartments(): Observable<Department[]> {
        return this.http.get<Department[]>(this.apiUrl);
    }

    addDepartment(department: Department): Observable<Department> {
        return this.http.post<Department>(this.apiUrl, department);
    }

    updateDepartment(department: Department): Observable<Department> {
        return this.http.put<Department>(`${this.apiUrl}/${department.id}`, department);
    }

    deleteDepartment(id: number): Observable<string> {
        return this.http.delete<string>(`${this.apiUrl}/${id}`,{responseType: 'text' as 'json'});
    }

}
