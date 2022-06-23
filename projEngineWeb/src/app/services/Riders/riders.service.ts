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


  private url: string = 'http://192.168.160.234:6869';

  constructor(private http: HttpClient) { }

  getActiveRiders(): Observable<any> {
    return this.http.get<any>(this.url + "/riders/activeRiders", httpOptions);
  }
}
