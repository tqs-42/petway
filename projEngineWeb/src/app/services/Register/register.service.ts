import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

const baseUrl = "http://localhost:8080/riders"

@Injectable({
  providedIn: 'root'
})

export class RegisterService {

  constructor(private http: HttpClient) { }

  register_rider(email: String, password: String, fullname: String, address: String) {
    type Rider = {
      email? : String;
      password? : String;
      fullname? : String;
      address? : String;
    }

    const data: Rider = {};
    data.email = email;
    data.password = password;
    data.fullname = fullname;
    data.address = address;

    return this.http.post(`${environment.baseURL}/riders/register`, data);
  }
}
