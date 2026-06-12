import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormField } from '../../models/formField';

@Component({
  selector: 'app-form-input',
  imports: [ReactiveFormsModule],
  templateUrl: './form-input.html',
  styleUrl: './form-input.scss',
})
export class FormInput implements OnInit, OnChanges {
  @Input() fields: FormField[] = [];
  @Input() initialData: any = null; // Used in case of update/edit mode
  @Input() submitLabel = 'Save';
  @Output() formSubmit = new EventEmitter<any>();

  dynamicForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.buildForm();
  }

  ngOnChanges(changes: SimpleChanges): void {
    // Automatically update the form when 'initialData' changes (like entering Edit Mode)
    if (changes['initialData'] && this.dynamicForm) {
      if (this.initialData) {
        this.dynamicForm.patchValue(this.initialData);
      } else {
        this.dynamicForm.reset();
      }
    }
  }

  buildForm(): void {
    const group: any = {};

    this.fields.forEach(field => {
      const validators = field.required ? [Validators.required] : [];
      group[field.name] = ['', validators];
    });

    this.dynamicForm = this.fb.group(group);

    // If initial data of an edit was passed, fill the form
    if (this.initialData) {
      this.dynamicForm.patchValue(this.initialData);
    }
  }

  onSubmit(): void {
    if (this.dynamicForm.valid) {
      this.formSubmit.emit(this.dynamicForm.value);
    } else {
      this.dynamicForm.markAllAsTouched();
    }
  }
}
