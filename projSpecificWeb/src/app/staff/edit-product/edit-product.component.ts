import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CategoryService } from './../../services/category.service';
import { Category } from './../../interfaces/Category';
import { ProductService } from './../../services/product.service';
import { Product } from './../../interfaces/Product';
import { UserService } from './../../services/user.service';
import { map } from 'rxjs';
import { Store } from 'src/app/interfaces/Store';


@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
  categories: Category[] = [];
  product= {} as Product;
  produto: Product[] = [];

  constructor(public userService:UserService,private productService: ProductService, private categoryService: CategoryService, private router: Router, private active_route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getCategories();
    this.active_route.paramMap.subscribe(parameterMap => {
       const id = parameterMap.get("id");
       this.getProduct(Number(id));
    })

    console.log("O MEU PRODUTO");
    console.log(this.product);
    console.log(this.produto);
  }

  private getProduct(id: number) {
    //ta a dar merda aqui q nao consigo guardar as cenas na variavel product
    this.productService.getProduct(id).subscribe(producti => this.produto = producti);

  }

  getCategories():void{
    this.categoryService.getAll().subscribe(
        categories => {
          this.categories = categories
        }
      );
  }

  editProduct(): void{
    if (this.product.name != "" && this.product.description != "" && this.product.price != 0) {
      if (this.userService.manager?.store.id) {
        this.productService.createProduct(this.product.category.name, this.product.name, this.product.description, this.product.price, this.userService.manager?.store.id ,this.product.stock, this.product.image);
      }

      //this.router.navigateByUrl('/system/product')
    }
  }
}