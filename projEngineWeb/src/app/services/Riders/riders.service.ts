import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};


@Injectable({
  providedIn: 'root'
})
export class RidersService {

  private baseUrl = "http://localhost:8080/";

  constructor(private http: HttpClient) { }

  getActiveRiders(): Observable<any> {
    const url = this.baseUrl;
    return this.http.get<any>(url + "all-active-riders", httpOptions);
  }
}
