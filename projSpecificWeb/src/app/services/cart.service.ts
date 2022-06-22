import { RequestProducts } from './../interfaces/RequestProducts';
import { CartProduct } from './../interfaces/CartProduct';
import { environment } from './../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};
@Injectable({
  providedIn: 'root'
})
export class CartService {

  totalElements: number = 0

  constructor(private http: HttpClient) { }

  getAll() {
    let email = localStorage.getItem('userEmail');
    let result = this.http.get<RequestProducts[]>(environment.baseAPIPath + '/carts/user/' + email + '/products')

    result.subscribe(items => this.totalElements = items.reduce((acc, item) => acc += item.amount, 0))

    return result
  }

  getOne(id: number) {
    let email = localStorage.getItem('userEmail');
    return this.http.get<RequestProducts>(environment.baseAPIPath + '/carts/user/'+ email +'/product/' + id)
  }

  add(product: number, amount: number) {
    let email = localStorage.getItem('userEmail');
    return this.http.put<RequestProducts>(environment.baseAPIPath + '/carts/user/' + email + '/product/' + product + '/amount/' + amount, httpOptions)
  }

  delete(product: number) {
    return this.http.post<CartProduct>(environment.baseAPIPath + '/cart/', { product, amount: 0 })
  }
}
