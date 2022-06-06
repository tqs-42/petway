import { Router } from '@angular/router';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';

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


  constructor(private fb : FormBuilder, private authService: AuthenticationService, private userService: UserService, private router : Router) { }

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
    let error = false;

    console.log(this.loginForm.value.password.length);

    if (!error) {

      console.log(this.authService.login(this.loginForm));

      this.authService.login(this.loginForm).subscribe(
        res => {
          console.log(res)

          let fullname = res.toString().split(":")[1].replace("{" , "").split(",")[0].replace("'","").replace("'","");
          let dtype = res.toString().split(":")[2].replace("{" , "").split(",")[0].replace("'","").replace("'","").replace("}","").split(".")[3];
          console.log(dtype)

          //let myObj = JSON.parse(res.toString());
          //console.log(myObj); 
          this.userService.email = this.loginForm.value.email;
          this.userService.username = fullname;
          this.userService.dtype = dtype;
          this.router.navigate(['/system/dashboard'])
          console.log("Entrou no next");
        },
        () => {
          console.log( "Entrou no error");
          this.showError = true;
          this.message = 'Error.'
        }
      )

    }
  }

}
