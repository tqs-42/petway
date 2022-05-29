import { CartProduct } from './../interfaces/CartProduct';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  totalElements: number = 0

  constructor(private http: HttpClient) { }

  getAll() {
    let result = this.http.get<CartProduct[]>(environment.baseAPIPath + '/cart/')

    result.subscribe(items => this.totalElements = items.reduce((acc, item) => acc += item.amount, 0))

    return result
  }

  getOne(id: number) {
    return this.http.get<CartProduct>(environment.baseAPIPath + '/cart/' + id)
  }

  add(product: number, amount: number) {
    return this.http.post<CartProduct>(environment.baseAPIPath + '/cart/', { product, amount })
  }

  delete(product: number) {
    return this.http.post<CartProduct>(environment.baseAPIPath + '/cart/', { product, amount: 0 })
  }
}
