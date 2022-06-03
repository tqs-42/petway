import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  
  private baseUrl = 'http://127.0.0.1:8080/';
  private token = localStorage.getItem('token');

  constructor(private http: HttpClient) { }

  login(form: FormGroup) {

    let email = form.value.email;
    let password = form.value.password;

    return this.http.post(this.baseUrl + 'login', { email, password },  { 
      headers: new HttpHeaders({
        'Authorization': 'Token ' + this.token,
        'Content-Type': 'application/json',
      }),
    });

  }


}
