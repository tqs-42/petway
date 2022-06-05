import { Component, OnInit } from '@angular/core';
import { Rider } from 'src/app/classes/Rider';
import { RidersService } from 'src/app/services/Riders/riders.service';

@Component({
  selector: 'app-riders',
  templateUrl: './riders.component.html',
  styleUrls: ['./riders.component.css']
})
export class RidersComponent implements OnInit {

  riders: Rider[] = [];

  constructor(private riderService: RidersService) { }

  ngOnInit(): void {
    this.getActiveRiders();
  }

  getActiveRiders() {
    this.riderService.getActiveRiders().subscribe(
      data => {
        console.log(data)
        this.riders = data;
      }
    )

  }

}