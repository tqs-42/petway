import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};


@Injectable({
  providedIn: 'root'
})
export class RidersService {

  constructor(private http: HttpClient) { }

  getActiveRiders(): Observable<any> {
    return this.http.get<any>(environment.baseURL + "riders/all-active-riders", httpOptions);
  }
}
