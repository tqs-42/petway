import { Router } from '@angular/router';
import { UserService } from './user.service';
import { LoginResponse } from './../interfaces/responses/LoginResponse';
import { User } from './../interfaces/User';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private userService: UserService, private router: Router) {
    this.userService.setToken(localStorage.getItem('token'))
    this.loadProfile()
  }

  signup(username: string, password: string) {
    this.http.post<User>(environment.baseAPIPath + '/signup', { username, password }).subscribe(response => console.log(response))
  }

  login(username: string, password: string) {
    /*
    this.http.post<LoginResponse>(environment.baseAPIPath + '/login', { username, password }).subscribe(response => {
      localStorage.setItem('token', response.token)
      this.userService.setToken(response.token)
      this.loadProfile()
    })
    */
    this.userService.username = "carlos";

  }

  logout() {
    this.userService.setToken(null)
    localStorage.removeItem('token')
    this.router.navigateByUrl('/')
  }

  loadProfile() {
    if (this.userService.token.value != null) this.http.get<User>(environment.baseAPIPath + '/profile').subscribe(user => this.userService.setUser(user))
  }

}
