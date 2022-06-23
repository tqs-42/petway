import { Component, OnInit } from '@angular/core';
import { Delivery } from 'src/app/classes/Delivery';
import { RidersService } from 'src/app/services/Riders/riders.service';
import { Rider } from 'src/models/Rider';

@Component({
  selector: 'app-rider',
  templateUrl: './rider.component.html',
  styleUrls: ['./rider.component.css']
})
export class RiderComponent implements OnInit {

  orders! : any[];
  email!:string;
  rider!: Rider;

  constructor(private riderService : RidersService) { }

  ngOnInit(): void {
    let url = window.location.href;
    this.email = url.split('/')[4];
    console.log(url.split('/'));
    this.getRiderInfo();
    this.getRiderOrders();
  }

  getRiderInfo() {
    this.riderService.getRider(this.email).subscribe(data => {
      this.rider = data;
    });
  }

  getRiderOrders() {
    this.riderService.getRiderDeliveries(this.email).subscribe(data => {
      this.orders = data;
    });
  }

}
