import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'roleLabel',
  standalone: true
})
export class RoleLabelPipe implements PipeTransform {
  private labels: Record<string, string> = {
    BACKEND_DEVELOPER:    'Backend Developer',
    FRONTEND_DEVELOPER:   'Frontend Developer',
    PROJECT_MANAGER:      'Project Manager',
    BUSINESS_ANALYST:     'Business Analyst',
    TECHNICAL_LEAD:       'Technical Lead',
    HR_SPECIALIST:        'HR Specialist',
    TECHNICAL_CONSULTANT: 'Technical Consultant',
    DEVELOPER:            'Developer',
    MANAGER:              'Manager',
    ANALYST:              'Analyst',
  };

  transform(value: string): string {
    return this.labels[value] ?? value;
  }
}
