import { UserService } from './../../services/user.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ViewChild } from '@angular/core';

@Component({
  selector: 'app-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  @ViewChild('loginModalClose') loginModalClose: any;
  @ViewChild('registerModalClose') registerModalClose: any;
  registerForm!: FormGroup;
  loginForm!: FormGroup;
  showMsgRegister: Boolean = false;
  showErrorLogin: Boolean = false;
  errorLogin: Boolean = false;
  messageRegister: String = 'Error.';
  messageLogin: String = 'Error.';

  constructor(
    private fb: FormBuilder,
    private authService: AuthenticationService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      address: [null, [Validators.required]],
      fullname: [null, [Validators.required]],
      password: [null, [Validators.required, Validators.minLength(8)]],
      password_repeat: [null, [Validators.required, Validators.minLength(8)]],
    });

    this.loginForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required, Validators.minLength(8)]],
    });
  }

  register(): void {
    if (
      this.registerForm.value.password !=
      this.registerForm.value.password_repeat
    ) {
      this.showMsgRegister = true;
      this.errorLogin = true;
      this.messageRegister = 'Passwords are not equal';
    } else if (this.registerForm.invalid) {
      this.showMsgRegister = true;
      this.errorLogin = true;
      if (this.registerForm.value.password !=null && this.registerForm.value.password.length < 8) {
        this.messageRegister = 'Password must be longer than 8!';
        this.showMsgRegister = true;
        this.errorLogin = true;
      } else{
        this.messageRegister = 'Error. All fields must be filled correctly.';
      }
    } else {
      this.authService.register(this.registerForm).subscribe({
        next: () => {
          this.showMsgRegister = true;
          this.messageRegister = 'Registration successful!';
        },
        error: () => {
          this.showMsgRegister = true;
          this.errorLogin = true;
          this.messageRegister = 'Error.';
        },
      });
    }
  }

  login(): void {
    if (this.loginForm.invalid) {
      this.showErrorLogin = true;
      this.messageLogin = 'Error. All fields must be filled correctly.';
    } else {
      this.authService.login(this.loginForm).subscribe(
        (res) => {
          if (res.hasOwnProperty('address')) {
            this.userService.setClient(res);
            localStorage.setItem('userEmail',res['email']);
            this.loginModalClose.nativeElement.click();
            this.router.navigate(['/']);
            this.showMsgRegister = false;
            this.showErrorLogin = false;
            this.loginForm.reset();
            this.registerForm.reset();
          } else if (res.hasOwnProperty('store')) {
            this.userService.setManager(res);
            localStorage.setItem('userEmail',res['email']);
            this.loginModalClose.nativeElement.click();
            this.router.navigate(['/system/dashboard']);
            this.showMsgRegister = false;
            this.showErrorLogin = false;
            this.registerForm.reset();
            this.loginForm.reset();
          }else{
            this.showErrorLogin = true;
          }
        },
        () => {
          this.showErrorLogin = true;
          this.messageLogin = 'Error. Wrong credentials!';
        }
      );
    }
  }
}
