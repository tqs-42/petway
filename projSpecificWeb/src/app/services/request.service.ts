import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class RequestService {
  private url: string = 'http://localhost:6868';

  constructor(private http: HttpClient) { }

  addRequest() {
    let userEmail = localStorage.getItem('userEmail');
    return this.http.post<any>(this.url + '/requestes/add', {"userEmail": userEmail}, httpOptions).subscribe(response => {
      console.log("AQUIIIIIIIIIIIIIIIIIIIIIIIIIIIII")
      console.log(response)
      this.http.post<any>(this.url + '/requestEvents/add', {"userEmail": userEmail}, httpOptions).subscribe(response => console.log(response))
    })
  }

  deleteRequestProducts(){
    let userEmail = localStorage.getItem('userEmail');
    this.http.delete<any>(this.url + "/requestesProducts/delete/" + userEmail, httpOptions).subscribe(response => console.log(response))

  }

}
