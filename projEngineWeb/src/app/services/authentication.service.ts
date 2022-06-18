import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { environment } from 'src/environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import { Person } from '../classes/Person';



@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private token = localStorage.getItem('token');
  userValue: any;

  private currentUserSubject: BehaviorSubject<Person>;
  public currentUser: Observable<Person>;

  constructor(private http: HttpClient) { 
    this.currentUserSubject = new BehaviorSubject<Person>(JSON.parse(localStorage.getItem('currentUser')!));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  test() {
    return this.http.get(environment.baseURL + 'riders/all-active-riders');
  }

  login(form: FormGroup) {

    let email = form.value.email;
    let password = form.value.password;

    return this.http.post(environment.baseURL + 'login', { email, password });

  }

  register(form: FormGroup) {

    let fullname = form.value.fullname;
    let address = form.value.address;
    let email = form.value.email;
    let password = form.value.password;

    let data = { fullname, address, email, password }

    return this.http.post(environment.baseURL + "register", data);
  }
}
