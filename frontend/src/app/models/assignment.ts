export interface Assignment {
  id: number;
  employeeId: number;
  employeeName: string;
  projectId: number;
  projectName: string;
  role: string;
}

export interface AssignmentRequest {
  EmployeeId: number;
  projectId: number;
  role: string;
}
