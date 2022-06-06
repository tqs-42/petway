import { Router } from '@angular/router';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  registerForm !: FormGroup;
  showError : Boolean = false;
  message: String = 'Error.';


  //login: { username: string, password: string } = { username: "", password: "" }
  // signup: { username: string, password: string } = { username: "", password: "" }

  // doLogin() {
  //   this.authService.login(this.login.username, this.login.password)
  // }

  constructor(private fb : FormBuilder, private authService: AuthenticationService, private router : Router) { }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      address: [null, [Validators.required]],
      fullname: [null, [Validators.required]],
      password:[null, [Validators.required, Validators.minLength(8)]],
      password_repeat:[null, [Validators.required, Validators.minLength(8)]]
    });
  }

  submit(): void {


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

}
