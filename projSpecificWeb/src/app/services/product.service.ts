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

  private baseUrl = "http://localhost:8080/products";

  constructor(private http: HttpClient, private userService:UserService) {
    let email = localStorage.getItem('userEmail');
    if (email != null) {
      this.http.get<any>(environment.baseAPIPath + '/users/byEmail/' + email).subscribe(
        (res) => {
          console.log(res)
          if (res.hasOwnProperty('cart')) {
            this.userService.setClient(res);
          } else if (res.hasOwnProperty('store')) {
            this.userService.setManager(res);
          }
        }
      );
    }
  }

  getProducts(): Observable<Product[]> {
    const url = this.baseUrl;
    console.log(this.http.get<Product[]>(url + "/products"))
    return this.http.get<Product[]>(url + "/products");
  }

  getAllSorting(sort:string){
    return this.http.get<Product[]>(environment.baseAPIPath + '/product/?ordering=' + sort)
  }

  getAll(name = "" , category = "") {
    return this.http.get<Product[]>(environment.baseAPIPath + '/product', {params: {search: name, category}})
  }

  getOne(id: number) {
    return this.http.get<Product>(environment.baseAPIPath + '/product/' + id)
  }

  createProduct(category: string, name: string, description: string, price: number, store: number, stock: number, image: string) {
    this.http.post<Product>(this.baseUrl + "/add", {  "category": category, "name": name, "description": description, "store": store, "stock": stock , "price": price, "image": image}, httpOptions).subscribe(response => console.log("sou a resposta --- " + response))
  }

  updateProduct(product:Product) {
    this.http.put<Product>(this.baseUrl + "/"+ product.id, { "id": product.id, "name": product.name, "description": product.description, "price": product.price, "isActive": product.isActive, "category": product.category.id }, httpOptions).subscribe(response => console.log(response))
  }

}
