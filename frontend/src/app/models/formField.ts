export interface FormOption {
  label: string;
  value: any;
}
export interface FormField {
  name: string;
  label: string;
  type: 'text' | 'email' | 'number' | 'date' | 'select';
  required?: boolean;
  options?: FormOption[];
}
