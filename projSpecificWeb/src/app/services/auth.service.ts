import { Router } from '@angular/router';
import { UserService } from './user.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../interfaces/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private userService: UserService, private router: Router) {
  }

  user: User | undefined;

  logout() {
  }

  loadProfile() {
    //if (this.userService.token.value != null) this.http.get<User>(environment.baseAPIPath + '/profile').subscribe(user => this.userService.setUser(user))
  }

}
