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

  @Output() edit = new EventEmitter<any>();

  @Output() delete = new EventEmitter<any>();

}
