import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private baseUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) { }

  register(form: FormGroup) {

    let fullname = form.value.fullname;
    let address = form.value.address;
    let email = form.value.email;
    let password = form.value.password;

    let data = { fullname, address, email, password }

    console.log(data);

    return this.http.post(this.baseUrl + 'api/specific/addClient', data);

  }

}
