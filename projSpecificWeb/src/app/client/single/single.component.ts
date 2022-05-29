import { Product } from './../../interfaces/Product';
import { ProductService } from './../../services/product.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-single',
  templateUrl: './single.component.html',
  styleUrls: ['./single.component.css']
})
export class SingleComponent implements OnInit {

  details: Product = { id: parseInt(location.href.split('/')[location.href.split('/').length-1]), name: "", description: "", price: 0, category: { id: 0, name: "", isActive: true }, isActive: true }

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.productService.getOne(this.details.id).subscribe(prod => this.details = prod)
  }

}
