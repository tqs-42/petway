import { environment } from './../../environments/environment';
import { Order } from './../interfaces/Order';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserService } from './user.service';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient, private userService:UserService) {
    let email = localStorage.getItem('userEmail');
    if (email != null) {
      this.http.get<any>(environment.baseAPIPath + '/users/byEmail/' + email).subscribe(
        (res) => {
          console.log(res)
          if (res.hasOwnProperty('address')) {
            this.userService.setClient(res);
          } else if (res.hasOwnProperty('store')) {
            this.userService.setManager(res);
          }
        }
      );
    }
  }

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
