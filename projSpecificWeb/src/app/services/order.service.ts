import { CartService } from './cart.service';
import { environment } from './../../environments/environment';
import { Order } from './../interfaces/Order';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserService } from './user.service';
import { ProductInfo } from './../interfaces/ProductInfo';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient, private userService:UserService, public cartService:CartService) {
    let email = localStorage.getItem('userEmail');
    let dtype = localStorage.getItem('dtype');
    if (email != null && dtype != null && dtype === "Client") {
      this.userService.setClient({ "email": email, fullname: ''})
    } else if (email != null && dtype != null && dtype === "Manager") {
      this.userService.setManager({ "email": email, store: { id: 0, name: '', address : '', active: true}, fullname: '' })
    }
  }

  getAll() {
    return this.http.get<Order[]>(environment.baseAPIPath + '/requestEvents/' + localStorage.getItem('userEmail'))
  }

  getOrdersByUser(userid: string) {
    return this.http.get<Order[]>(environment.baseAPIPath + '/ordersUser/?user=' + userid)
  }

  getProductsInfo(orderid: string) {
    return this.http.get<ProductInfo[]>(environment.baseAPIPath + '/requestEvents/products/' + orderid)
  }

  getOrdersById(reqId: number) {
    return this.http.get<Order>(environment.baseAPIPath + '/requestEvents/id/' + reqId)
  }

  getDetails(id: number) {
    return this.http.get<Order>(environment.baseAPIPath + '/order/' + id)
  }

  create() {
    return this.http.post<Order>(environment.baseAPIPath + '/order/', {})
  }

  changeState(order:Order) {
    this.http.put<Order>(environment.baseAPIPath + "/order/changestate/"+ order.id, httpOptions).subscribe(response => console.log(response))
  }

  cancelOrder(order: Order) {
    this.http.delete<Order>(environment.baseAPIPath + "/order/changestate/"+ order.id, httpOptions).subscribe(response => console.log(response))
  }
}
