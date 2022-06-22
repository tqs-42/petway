import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from './../../environments/environment';
import { User } from '../interfaces/User';
import { UserService } from './user.service';

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
    if (email != null && dtype != null && dtype === "Client") {
      this.userService.setClient({ "email": email, address: '', fullname: ''})
    } else if (email != null && dtype != null && dtype === "Manager") {
      this.userService.setManager({ "email": email, store: { id: 0, name: '', address : '', active: true}, fullname: '' })
    }
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


