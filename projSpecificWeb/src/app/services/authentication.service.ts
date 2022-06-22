import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from './../../environments/environment';
import { User } from '../interfaces/User';
import { UserService } from './user.service';
import { Store } from '../interfaces/Store';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private userSubject: BehaviorSubject<User>;
  public user: Observable<User>;

  constructor(private userService: UserService, private http: HttpClient) {

    this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();

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

  public get userValue(): User {
    return this.userSubject.value;
  }

  login(form: FormGroup) : Observable<any> {

    let email = form.value.email;
    let password = form.value.password;
    console.log(email);
    console.log(password);

    return this.http.post<any>(environment.baseAPIPath + '/login', { email, password });
  }

  getStoreFromManager(managerEmail: String) : Observable<any> {
    return this.http.get<Store>(environment.baseAPIPath + "/manageres/user/" + managerEmail +"/store", httpOptions);
  }

  getUserFullName(email: String) : Observable<any> {
    return this.http.get<String>(environment.baseAPIPath + "/users/user/" + email +"/fullname", httpOptions);
  }

  register(form: FormGroup) {

    let fullname = form.value.fullname;
    let address = form.value.address;
    let email = form.value.email;
    let password = form.value.password;

    let data = { fullname, address, email, password }

    console.log(data);

    return this.http.post(environment.baseAPIPath + '/registerClient', data);

  }

}


