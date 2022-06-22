import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';
import { Product } from './../interfaces/Product';
import { Category } from '../interfaces/Category';
import { UserService } from './user.service';
import { AuthenticationService } from './authentication.service';
import { Store } from '../interfaces/Store';


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private authService: AuthenticationService,private http: HttpClient, private userService:UserService) {
    let email = localStorage.getItem('userEmail');
    let dtype = localStorage.getItem('dtype');
    let loja = localStorage.getItem('store');
    let userFullName = localStorage.getItem('userFullName');
    if (email != null && dtype != null && dtype === "Client" && userFullName != null) {
      this.userService.setClient({ "email": email, fullname: userFullName})
    } else if (email != null && dtype != null && dtype === "Manager" && loja != null && userFullName != null) {
          this.userService.setManager({ "email": email, store: this.forceCast<Store>(loja), fullname: userFullName })
    }
  }

  forceCast<Store>(input: any): Store {
    return input;
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
