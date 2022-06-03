import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  
  private baseUrl = 'http://localhost:6869/';
  private token = localStorage.getItem('token');

  constructor(private http: HttpClient) { }

  login(form: FormGroup) {

    let email = form.value.email;
    let password = form.value.password;

    return this.http.post(this.baseUrl + 'login', { email, password });

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


}
