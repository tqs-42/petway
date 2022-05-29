import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-info-box',
  templateUrl: './info-box.component.html',
  styleUrls: ['./info-box.component.css']
})
export class InfoBoxComponent implements OnInit {

  @Input() icon! : string;
  @Input() title! : string;
  @Input() description! : string;

  constructor() { }

  ngOnInit(): void {
  }

}
