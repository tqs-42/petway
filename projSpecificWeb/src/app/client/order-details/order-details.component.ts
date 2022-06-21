import { Order } from './../../interfaces/Order';
import { ProductInfo } from './../../interfaces/ProductInfo';
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
  product: ProductInfo | null = null

  constructor(private route: ActivatedRoute, private orderService: OrderService) { }

  ngOnInit(): void {
    this.orderService.getOrdersById(this.orderId).subscribe(order => {
      this.order = order
    })

    // this.orderService.getProductsInfo(this.orderId).subscribe(product => {
    //   this.product = product;
    // })
  }
}
