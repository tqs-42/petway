import { Router } from '@angular/router';
import { CategoryService } from './../../services/category.service';
import { Category } from './../../interfaces/Category';
import { ProductService } from './../../services/product.service';
import { Product } from './../../interfaces/Product';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';


@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrls: ['./new-product.component.css']
})
export class NewProductComponent implements OnInit {
  categories: Category[] = [];
  product!: { category: string, name: string, description: string, price: number, stock: number, image: string, store: number};

  constructor(private productService: ProductService, private categoryService: CategoryService, private router: Router) { }

  ngOnInit(): void {
    this.getCategories();
    //no placeholder da store vai ter de ficar a loja deste manager e dar disable, ou se calhar so nao se mete la e fica internamente
    this.product = {category: "", name: "", description:"", price: 0, stock: 0, image: "", store: 0}
  }

  getCategories():void{
    this.categoryService.getAll().subscribe(
        categories => {
          console.log(categories)
          this.categories = categories
        }
      );
  }

  createProduct(): void{
    if (this.product.name != "" && this.product.description != "" && this.product.price != 0) {
      console.log(this.product.category);
      this.productService.createProduct(this.product.category, this.product.name, this.product.description, this.product.price, this.product.store ,this.product.stock, this.product.image);
      //this.router.navigateByUrl('/system/product')
    }
  }
}
