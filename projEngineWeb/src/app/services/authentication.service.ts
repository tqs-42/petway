import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { environment } from 'src/environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import { Person } from '../classes/Person';



@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private url: string = 'http://192.168.160.234:6869/';

  private userSubject: BehaviorSubject<Person>;
  public user: Observable<Person>;

  constructor(private http: HttpClient) { 
    this.userSubject = new BehaviorSubject<Person>(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
  }

  public get userValue(): Person {
    return this.userSubject.value;
  }

  login(form: FormGroup) {

    let email = form.value.email;
    let password = form.value.password;

    return this.http.post(this.url + 'login', { email, password });

  }

  register(form: FormGroup) {

    let fullname = form.value.fullname;
    let address = form.value.address;
    let email = form.value.email;
    let password = form.value.password;

    let data = { fullname, address, email, password }

    return this.http.post(this.url + "register", data);
  }
}
