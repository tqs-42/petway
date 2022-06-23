import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Delivery } from 'src/app/classes/Delivery';
import { environment } from 'src/environments/environment';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};


@Injectable({
  providedIn: 'root'
})
export class RidersService {

  constructor(private http: HttpClient) { }

  getAllRiders(): Observable<any> {
    return this.http.get<any>(environment.baseURL + "riders/allRiders", httpOptions);
  }

  getActiveRiders(): Observable<any> {
    return this.http.get<any>(environment.baseURL + "riders/activeRiders", httpOptions);
  }

  getRider(email : string): Observable<any> {
    return this.http.get<any>(environment.baseURL + "riders/rider?email="+email, httpOptions);
  }

  getRiderDeliveries(email : string): Observable<any[]> {
    return this.http.get<any[]>(environment.baseURL + "deliveries/riderDeliveries?email="+email, httpOptions);
  }

}
