import { CartService } from '../../services/cart.service';
import { CartProduct } from '../../interfaces/CartProduct';
import { UserService } from '../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  manager! : boolean;
  client! : boolean;

  constructor(public userService: UserService, public authenticationService : AuthenticationService, public cartService: CartService) { }

  ngOnInit(): void {

  }

}
