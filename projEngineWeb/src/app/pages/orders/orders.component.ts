import { Component, OnInit } from '@angular/core';
import { Delivery } from 'src/app/classes/Delivery';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { OrdersService } from 'src/app/services/orders.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  orders! : Delivery[];

  constructor(private autService : AuthenticationService, private ordersService : OrdersService) { }

  ngOnInit(): void {
    this.getOrders(); 
  }

  getOrders() {
    this.ordersService.getOrders().subscribe(
      data => {
        this.orders = data;
        console.log("orders",data)
      }
    )
  }

}
