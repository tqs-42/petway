import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class StoresService {

  private baseUrl = "http://localhost:8080/";

  constructor(private http: HttpClient) { }

  getAllStores(): Observable<any> {
    const url = this.baseUrl;
    return this.http.get<any>(url + "stores/get-all-stores", httpOptions);
  }
}
