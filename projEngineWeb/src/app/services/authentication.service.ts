import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { HttpClient } from "@angular/common/http";
import { Person } from '../classes/Person';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  
  private baseUrl = 'http://localhost:6869/';

  private currentUserSubject: BehaviorSubject<Person>;
  public currentUser: Observable<Person>;

  constructor(private http: HttpClient) { 
    this.currentUserSubject = new BehaviorSubject<Person>(JSON.parse(localStorage.getItem('currentUser')!));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  test() {
    return this.http.get(this.baseUrl + 'oi');
  }

  login(form: FormGroup) {

    let email = form.value.email;
    let password = form.value.password;

    return this.http.post(this.baseUrl + 'login', { email, password }).pipe(map((user) => {
      // store user details and jwt token in local storage to keep user logged in between page refreshes
      localStorage.setItem('currentUser', JSON.stringify(user));
      this.currentUserSubject.next(<Person> user);
      return user;
    }));
  }

  register(form: FormGroup) {

    let fullname = form.value.fullname;
    let address = form.value.address;
    let email = form.value.email;
    let password = form.value.password;

    let data = { fullname, address, email, password }

    console.log(data);

    return this.http.post(this.baseUrl + 'register', data);

  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null!);
  }

  public get currentUserValue(): Person {
    return this.currentUserSubject.value;
  }


}
