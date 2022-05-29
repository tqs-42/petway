import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/interfaces/Order';
import { OrderService } from 'src/app/services/order.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-order-detail-staff',
  templateUrl: './order-detail-staff.component.html',
  styleUrls: ['./order-detail-staff.component.css']
})
export class OrderDetailStaffComponent implements OnInit {

  orderId = this.route.snapshot.params['id']
  order: Order | null = null

  constructor(private route: ActivatedRoute, private orderService: OrderService) { }

  ngOnInit(): void {
    this.orderService.getDetails(this.orderId).subscribe(order => this.order = order)
  }

}
