import { Order } from './../../interfaces/Order';
import { OrderService } from './../../services/order.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-orders-cli',
  templateUrl: './orders-cli.component.html',
  styleUrls: ['./orders-cli.component.css']
})
export class OrdersCliComponent implements OnInit {

  constructor(private orderService: OrderService) { }

  orders: Order[] = []

  ngOnInit(): void {
    this.orderService.getAll().subscribe(orders => this.orders = orders)
  }

}
