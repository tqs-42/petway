import { Router } from '@angular/router';
import { UserService } from './../../services/user.service';
import { User } from './../../interfaces/User';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-staff-accounts',
  templateUrl: './staff-accounts.component.html',
  styleUrls: ['./staff-accounts.component.css']
})
export class StaffAccountsComponent implements OnInit {

  staffs: User[] = [];

  constructor(private UserService:UserService, private router: Router) { }

  ngOnInit(): void {
    this.getStaffs()
  }

  getStaffs():void {
    this.UserService.getAllStaff().subscribe(staffs => this.staffs = staffs)
  }

  deleteStaff(staffId: number): void {
    this.UserService.deleteStaff(staffId).subscribe(() => window.location.reload())
  }

}
