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

    let map = new Map<String, String>();
    map.set("email", email);
    map.set("password", password);
    map.set("fullname", fullname);
    map.set("address", address);

    return this.http.post(`${environment.baseURL}/addRider`, map);
  }
}
