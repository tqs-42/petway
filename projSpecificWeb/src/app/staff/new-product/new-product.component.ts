import { Router } from '@angular/router';
import { CategoryService } from './../../services/category.service';
import { Category } from './../../interfaces/Category';
import { ProductService } from './../../services/product.service';
import { Component, OnInit } from '@angular/core';
import { UserService } from './../../services/user.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrls: ['./new-product.component.css']
})
export class NewProductComponent implements OnInit {
  myForm = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    file: new FormControl('', [Validators.required]),
    fileSource: new FormControl('', [Validators.required])
  });
  
  categories: Category[] = [];
  product!: { category: string, name: string, description: string, price: number, stock: number, image: string, store: number};

  constructor(public userService:UserService,private productService: ProductService, private categoryService: CategoryService, private router: Router, private http: HttpClient) { }

  get f(){
    return this.myForm.controls;
  }

  onFileChange(event: any) {
  
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.myForm.patchValue({
        fileSource: file
      });
    }
  }
    
  ngOnInit(): void {
    this.getCategories();
    //no placeholder da store vai ter de ficar a loja deste manager e dar disable, ou se calhar so nao se mete la e fica internamente
    this.product = {category: "", name: "", description:"", price: 0, stock: 0, image: "", store: 0}
  }

  getCategories():void{
    this.categoryService.getAll().subscribe(
        categories => {
          this.categories = categories
        }
      );
  }

  createProduct(): void{
    if (this.product.category != "" && this.product.name != "" && this.product.description != "" && this.product.price != 0) {
      if (this.userService.manager?.store.id) {
        this.product.image = this.product.image.replace('C:\\fakepath\\','');
        this.productService.createProduct(this.product.category, this.product.name, this.product.description, this.product.price, this.userService.manager?.store.id ,this.product.stock, this.product.image);
        const formData = new FormData();
        formData.append('file', this.myForm.get('fileSource')!.value);

        console.log(formData)
        this.http.post('http://localhost:8001/upload.php', formData)
          .subscribe(res => {
            console.log(res);
            alert('Uploaded Successfully.');
        })
      }
    }
  }
}
