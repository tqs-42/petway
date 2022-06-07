import { CartService } from '../../services/cart.service';
import { CartProduct } from '../../interfaces/CartProduct';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  constructor(public userService: UserService, public authService: AuthService, public cartService: CartService) { }

  ngOnInit(): void {
  }

}
