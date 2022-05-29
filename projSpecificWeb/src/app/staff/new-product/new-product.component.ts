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
  product!: { category: number, name: string, description: string, price: number};

  constructor(private productService: ProductService, private categoryService: CategoryService, private router: Router) { }

  ngOnInit(): void {
    this.getCategories();
    this.product = {category: 0, name: "", description:"", price: 0}
  }

  getCategories():void{
    this.categoryService.getAll().subscribe(categories => this.categories = categories);
  }

  createProduct(): void{
    if (this.product.name != "" && this.product.description != "" && this.product.price != 0) {
        this.productService.createProduct(this.product.category, this.product.name, this.product.description, this.product.price);
        this.router.navigateByUrl('/system/product')
    }
  }
}
