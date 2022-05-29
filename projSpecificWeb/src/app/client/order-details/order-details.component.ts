import { Order } from './../../interfaces/Order';
import { OrderService } from './../../services/order.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {

  orderId = this.route.snapshot.params['id']
  order: Order | null = null

  constructor(private route: ActivatedRoute, private orderService: OrderService) { }

  ngOnInit(): void {
    this.orderService.getDetails(this.orderId).subscribe(order => this.order = order)
  }

}
