import { Product } from './../../interfaces/Product';
import { ProductService } from './../../services/product.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-show-product',
  templateUrl: './show-product.component.html',
  styleUrls: ['./show-product.component.css']
})
export class ShowProductComponent implements OnInit {
  product: Product | null = null

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.productService.getOne(parseInt(location.href.split('/')[location.href.split('/').length-1])).subscribe(product => this.product = product)
  }

}
