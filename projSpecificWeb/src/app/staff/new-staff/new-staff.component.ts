import { Router } from '@angular/router';
import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-new-staff',
  templateUrl: './new-staff.component.html',
  styleUrls: ['./new-staff.component.css']
})
export class NewStaffComponent implements OnInit {
  staff: {username: string, password: string, repeatPassword: string} = {username: "", password: "", repeatPassword: ""}
  diferentPassword: boolean = false
  constructor(private userService:UserService, private router:Router) { }

  ngOnInit(): void {
  }

  createStaff():void{
    this.diferentPassword = false
    if (this.staff.password != this.staff.repeatPassword) {
      this.diferentPassword = true
    }
    else{
      if (this.staff.username != "" && this.staff.password != "" && this.staff.repeatPassword != "") {
        this.userService.createUser(this.staff.username, this.staff.password).subscribe(() => this.router.navigateByUrl('/system/staff'))
      }
    }
  }

}
