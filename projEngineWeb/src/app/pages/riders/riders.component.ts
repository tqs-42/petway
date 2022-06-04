import { Component, OnInit } from '@angular/core';
import { RidersService } from 'src/app/services/Riders/riders.service';
import { Rider } from 'src/models/Rider';

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
        this.riders = data["riders"];
      }
    )

  }

}
