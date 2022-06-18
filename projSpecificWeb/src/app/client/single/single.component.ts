import { Product } from './../../interfaces/Product';
import { ProductService } from './../../services/product.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-single',
  templateUrl: './single.component.html',
  styleUrls: ['./single.component.css']
})
export class SingleComponent implements OnInit {

  id:number = parseInt(location.href.split('/')[location.href.split('/').length-1]);
  details:Product | undefined;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.productService.getOne(this.id).subscribe(prod => this.details = prod);
  }

}
