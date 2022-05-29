import { UserService } from './../../services/user.service';
import { User } from './../../interfaces/User';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-users-accounts',
  templateUrl: './users-accounts.component.html',
  styleUrls: ['./users-accounts.component.css']
})
export class UsersAccountsComponent implements OnInit {

  users: User[] = [];

  constructor(private UserService:UserService) { }

  ngOnInit(): void {
    this.getUsers()
  }

  getUsers():void {
    this.UserService.getAllUsers().subscribe(users => this.users = users)
  }


}
