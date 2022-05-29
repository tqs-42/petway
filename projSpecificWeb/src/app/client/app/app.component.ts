import { Router } from '@angular/router';
import { UserService } from './../../services/user.service';
import { CartService } from './../../services/cart.service';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  login: { username: string, password: string } = { username: "", password: "" }
  signup: { username: string, password: string } = { username: "", password: "" }

  doLogin() {
    this.authService.login(this.login.username, this.login.password)
  }

  doSignUp() {
    this.authService.signup(this.signup.username, this.signup.password)
  }

  constructor(private authService: AuthService, private cartService: CartService) { }

  ngOnInit(): void {
    this.cartService.getAll()
  }

}
