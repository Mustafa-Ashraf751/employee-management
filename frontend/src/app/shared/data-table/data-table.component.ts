import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-data-table',
  imports: [],
  templateUrl: './data-table.html',
  styleUrl: './data-table.scss',
})
export class DataTable {

  @Input() columns: string[] = [];

  @Input() data: any[] = [];

  @Input() showEdit: boolean = true;

  @Input() showDelete: boolean = true;

  @Output() edit = new EventEmitter<any>();

  @Output() delete = new EventEmitter<any>();

  formatHeader(column: string): string {
    const spaced = column.replace(/([A-Z])/g, ' $1').trim();
    return spaced.charAt(0).toUpperCase() + spaced.slice(1);
  }

}
