import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';
import { Product } from './../interfaces/Product';
import { Category } from '../interfaces/Category';
import { UserService } from './user.service';


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient, private userService:UserService) {
    let email = localStorage.getItem('userEmail');
    let dtype = localStorage.getItem('dtype');
    if (email != null && dtype != null && dtype === "Client") {
      this.userService.setClient({ "email": email, fullname: ''})
    } else if (email != null && dtype != null && dtype === "Manager") {
      this.userService.setManager({ "email": email, store: { id: 0, name: '', address : '', active: true}, fullname: '' })
    }
  }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(environment.baseAPIPath + "/products");
  }

  getAllSorting(sort:string){
    return this.http.get<Product[]>(environment.baseAPIPath + '/product/?ordering=' + sort)
  }

  getAll(name = "" , category = "") {
    return this.http.get<Product[]>(environment.baseAPIPath + '/product', {params: {search: name, category}})
  }

  getOne(id: number) {
    return this.http.get<Product>(environment.baseAPIPath + '/products/' + id)
  }

  createProduct(category: string, name: string, description: string, price: number, store: number, stock: number, image: string) {
    this.http.post<Product>(environment.baseAPIPath + "/products/add", {  "category": category, "name": name, "description": description, "store": store, "stock": stock , "price": price, "image": image}, httpOptions).subscribe(response => console.log("sou a resposta --- " + response))
  }

  updateProduct(product:Product) {
    this.http.put<Product>(environment.baseAPIPath + "/products/"+ product.id, { "id": product.id, "name": product.name, "description": product.description, "price": product.price, "category": product.category.id }, httpOptions).subscribe(response => console.log(response))
  }

}
