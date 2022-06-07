import { Manager } from './../../interfaces/Manager';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Client } from '../../interfaces/Client';


@Component({
  selector: 'app-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  registerForm !: FormGroup;
  loginForm !: FormGroup;
  showError : Boolean = false;
  message: String = 'Error.';
  client: Client | undefined;
  manager: Manager | undefined;

  constructor(private fb : FormBuilder, private authService: AuthenticationService, private router : Router) { }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      address: [null, [Validators.required]],
      fullname: [null, [Validators.required]],
      password:[null, [Validators.required, Validators.minLength(8)]],
      password_repeat:[null, [Validators.required, Validators.minLength(8)]]
    });

    this.loginForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      password:[null, [Validators.required, Validators.minLength(8)]],
    });
  }

  register(): void {
    let error = false;

    if (this.registerForm.value.password != this.registerForm.value.password_repeat) {
      this.showError = true;
      this.message = 'Passwords are not equal';
      error = true;
    } else if (this.registerForm.value.password.length < 8) {
      this.message = 'Password must be longer than 8!';
      this.showError = true;
      error = true;
    }else if (this.registerForm.invalid) {
      this.showError = true;
      error = true;
      this.message = 'Error. All fields must be filled.'
    }

    if (!error) {

      this.authService.register(this.registerForm).subscribe({
        next: () => {
          this.router.navigate(['/system/dashboard'])
        },
        error: () => {
          this.showError = true;
          this.message = 'Error.'
        }
      })

    }
  }

  login(): void {
    this.authService.login(this.loginForm).subscribe(
      res => {
        if (res.hasOwnProperty("cart")) {
          this.client = res;
          this.router.navigate(['/'])
        } else if (res.hasOwnProperty("store")) {
          this.manager = res;
          this.router.navigate(['/system/dashboard'])
        }
      },
      () => {
        this.showError = true;
        this.message = 'Error.'
      }
    )

  }

}
