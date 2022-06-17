import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { environment } from 'src/environments/environment';



@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  
  private baseUrl = 'http://localhost:8080/';
  private token = localStorage.getItem('token');
  userValue: any;

  //private currentUserSubject: BehaviorSubject<Person>;
  //public currentUser: Observable<Person>;

  constructor(private http: HttpClient) { 
    //this.currentUserSubject = new BehaviorSubject<Person>(JSON.parse(localStorage.getItem('currentUser')!));
    //this.currentUser = this.currentUserSubject.asObservable();
  }

  test() {
    return this.http.get(this.baseUrl + 'riders/all-active-riders');
  }

  login(form: FormGroup) {

    let email = form.value.email;
    let password = form.value.password;

    return this.http.post(this.baseUrl + 'riders/login', { email, password });

  }

  register(form: FormGroup) {

    let fullname = form.value.fullname;
    let address = form.value.address;
    let email = form.value.email;
    let password = form.value.password;

    let data = { fullname, address, email, password }

    return this.http.post(this.baseUrl + "riders/register", data);
  }
}
