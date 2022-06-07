import { Router } from '@angular/router';
import { UserService } from './user.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private userService: UserService, private router: Router) {
  }

  logout() {
    this.userService.username = null;
  }

  loadProfile() {
    //if (this.userService.token.value != null) this.http.get<User>(environment.baseAPIPath + '/profile').subscribe(user => this.userService.setUser(user))
  }

}
