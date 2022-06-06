import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'projEngineWeb';

  constructor(private authenticationService: AuthenticationService, private router: Router) {

    // if (this.authenticationService.currentUserValue) {
    //   this.router.navigate(['/']);
    // } else {
    //   this.router.navigate(['/login']);
    // }
  }

}
