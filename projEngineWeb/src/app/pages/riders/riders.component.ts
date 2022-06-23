import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RidersService } from 'src/app/services/Riders/riders.service';
import { Rider } from 'src/models/Rider';

@Component({
  selector: 'app-riders',
  templateUrl: './riders.component.html',
  styleUrls: ['./riders.component.css']
})
export class RidersComponent implements OnInit {

  riders: Rider[] = [];
  totalRiders : number = 0;
  activeRiders : number = 0;

  constructor(private riderService: RidersService, private router : Router) { }

  ngOnInit(): void {
    this.getRiders();
  }

  getRiders() {
    this.riderService.getActiveRiders().subscribe(
      data => {
        this.riders = data;
        this.totalRiders = this.riders.length;
        for (let rider of this.riders) {
          if (rider.isActive === true) this.activeRiders += 1;
        }
      }
    )

  }

  goToRider(email : string) {
    this.router.navigate(["rider/"+email]);
  }


}
