import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm! : FormGroup;

  constructor(private formBuilder : FormBuilder, private authenticationService : AuthenticationService, private router : Router) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  clearForm() {
    this.loginForm.reset();
  }

  login() {
    this.authenticationService.login(this.loginForm).subscribe((data) => {
      console.log(data);
      localStorage.setItem('user',JSON.stringify(data));
      this.router.navigate(['/']);
    });
  }

}
