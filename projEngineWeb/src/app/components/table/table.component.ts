import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {

  // dataSource comes from the parent component
  @Input() dataSource! : any[];

  constructor() { }

  ngOnInit(): void {
  }

}
