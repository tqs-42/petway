import { Product } from './../../interfaces/Product';
import { ProductService } from './../../services/product.service';
import { Component, OnInit } from '@angular/core';
import { CategoryService } from 'src/app/services/category.service';
import { Category } from 'src/app/interfaces/Category';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: Product[] = []
  categories: Category[] = []
  name = ""
  check = ""
  sort = ""
  
  constructor(private productService: ProductService, private categoryService: CategoryService) { }
  
  ngOnInit(): void {
    this.productService.getAll(this.name).subscribe(products => this.products = products)
    this.categoryService.getAll().subscribe(categories => this.categories = categories)
  }

  update_search(name: string):void {
    this.productService.getAll(this.name).subscribe(products => this.products = products)
  }
  
  change_category(): void {
    this.productService.getAll("", this.check).subscribe(products => this.products = products)
  }

  sorting(): void {
    this.productService.getAllSorting(this.sort).subscribe(products => this.products = products)
  }
}
