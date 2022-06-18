import { ProductService } from './../../services/product.service';
import { Product } from './../../interfaces/Product';
import { Component, OnInit } from '@angular/core';
import { UserService } from './../../services/user.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: Product[] = [];

  constructor(public userService:UserService, private productService:ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getProducts().subscribe(products => this.products = products);
  }

  desactiveProduct(productId: number):void{
    console.log("BOtao destivar produto pressionado")
    console.log(productId)
    this.products.forEach(product => {
      if (product.id == productId) {
        console.log(product)
        //this.productService.updateProduct(product)
      }

    });
  }

  activeProduct(productId: number):void{
    console.log("BOtao ativar produto pressionado")
    console.log(productId)
    this.products.forEach(product => {
      if (product.id == productId) {
        console.log(product)
        //this.productService.updateProduct(product)
      }

    });
  }

}
