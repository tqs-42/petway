import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/interfaces/Order';
import { ActivatedRoute } from '@angular/router';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-orders-by-users',
  templateUrl: './orders-by-users.component.html',
  styleUrls: ['./orders-by-users.component.css']
})
export class OrdersByUsersComponent implements OnInit {

  userid = this.route.snapshot.params['id']
  orders:Order[] = [];

  constructor(private route: ActivatedRoute, private orderService: OrderService) { }

  ngOnInit(): void {
    this.orderService.getOrdersByUser(this.userid).subscribe(orders => this.orders = orders);
  }

  changeState(orderId: number):void {
    this.orders.forEach(order => {
      if (order.id == orderId) {
        this.orderService.changeState(order)
        window.location.reload();
      }
    });

  }

  cancelOrder(orderId: number): void {
    this.orders.forEach(order => {
      if (order.id == orderId) {
        this.orderService.cancelOrder(order)
        window.location.reload();
      }
    });
  }


}
