import { CartService } from './cart.service';
import { environment } from './../../environments/environment';
import { Order } from './../interfaces/Order';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserService } from './user.service';
import { ProductInfo } from './../interfaces/ProductInfo';
import { AuthenticationService } from './authentication.service';
import { Store } from '../interfaces/Store';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private url: string = 'http://localhost:6868';

  constructor(private authService: AuthenticationService,private http: HttpClient, private userService:UserService, public cartService:CartService) {
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

  getAll() {
    return this.http.get<Order[]>(this.url + '/requestEvents/' + localStorage.getItem('userEmail'))
  }

  getOrdersByUser(userid: string) {
    return this.http.get<Order[]>(this.url + '/ordersUser/?user=' + userid)
  }

  getProductsInfo(orderid: string) {
    return this.http.get<ProductInfo[]>(this.url + '/requestEvents/products/' + orderid)
  }

  getOrdersById(reqId: number) {
    return this.http.get<Order>(this.url + '/requestEvents/id/' + reqId)
  }

  getDetails(id: number) {
    return this.http.get<Order>(this.url + '/order/' + id)
  }

  create() {
    return this.http.post<Order>(this.url + '/order/', {})
  }

  changeState(order:Order) {
    this.http.put<Order>(this.url + "/order/changestate/"+ order.id, httpOptions).subscribe(response => console.log(response))
  }

  cancelOrder(order: Order) {
    this.http.delete<Order>(this.url + "/order/changestate/"+ order.id, httpOptions).subscribe(response => console.log(response))
  }
}
