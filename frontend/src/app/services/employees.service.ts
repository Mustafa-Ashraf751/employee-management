import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import {Employee} from '../models/employees';

@Injectable({
  	providedIn: 'root',
})
export class EmployeeService {

    private apiUrl = `${environment.apiUrl}/employees`;

    constructor(private http: HttpClient) {}

    getEmployees(): Observable<Employee[]> {
        return this.http.get<Employee[]>(this.apiUrl);
    }

    addEmployee(employee: Employee): Observable<Employee> {
        return this.http.post<Employee>(this.apiUrl, employee);
    }

    updateEmployee(employee : Employee): Observable<Employee> {
        return this.http.put<Employee>(`${this.apiUrl}/${employee.id}`, employee);
    }

    deleteEmployee(id: number): Observable<string> {
      return this.http.delete<string>(`${this.apiUrl}/${id}`, { responseType: 'text' as 'json' });
    }

    getEmployeesByDepartment(departmentId: number): Observable<Employee[]> {
        return this.http.get<Employee[]>(`${this.apiUrl}/department/${departmentId}`);
    }

}
