import { environment } from './../../environments/environment';
import { Order } from './../interfaces/Order';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<Order[]>(environment.baseAPIPath + '/order/')
  }

  getOrdersByUser(userid: string) { 
    return this.http.get<Order[]>(environment.baseAPIPath + '/ordersUser/?user=' + userid)
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
