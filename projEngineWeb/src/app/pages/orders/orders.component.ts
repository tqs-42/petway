import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  constructor(private autService : AuthenticationService) { }

  ngOnInit(): void {
    this.test();
  }

  test() {
    this.autService.test().subscribe((data) => {
      console.log("uiiiiiiiiiiiiiiiiiiiiiiii")
      console.log(data)
    })
  }

}
