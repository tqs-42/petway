import { UserService } from './user.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private userService: UserService, private http: HttpClient) {
    let email = localStorage.getItem('userEmail');
    if (email != null) {
      this.http.get<any>(environment.baseAPIPath + '/users/byEmail/' + email).subscribe(
        (res) => {
          console.log(res)
          if (res.hasOwnProperty('address')) {
            this.userService.setClient(res);
          } else if (res.hasOwnProperty('store')) {
            this.userService.setManager(res);
          }
        }
      );
    }
   }

  login(form: FormGroup) : Observable<any> {

    let email = form.value.email;
    let password = form.value.password;

    return this.http.post<any>(environment.baseAPIPath + '/users/login', { email, password });
  }

  register(form: FormGroup) {

    let fullname = form.value.fullname;
    let address = form.value.address;
    let email = form.value.email;
    let password = form.value.password;

    let data = { fullname, address, email, password }

    console.log(data);

    return this.http.post(environment.baseAPIPath + '/clients/add', data);

  }

}
