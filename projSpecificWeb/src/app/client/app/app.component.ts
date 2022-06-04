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
  showConfirmPasswordError : Boolean = false;
  showError : Boolean = false;


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
      console.log("ESTOU A ENTRAR AQUI ???? " + this.registerForm.value.password + "   " + this.registerForm.value.password_repeat)
      this.showConfirmPasswordError = true;
      error = true;
    }

    if (this.registerForm.invalid) {
      this.showError = true;
      error = true;
    }

    if (!error) {
      console.log("nao TEM EROO -- " + error)

      this.authService.register(this.registerForm).subscribe({
        next: () => {
          this.router.navigate(['/system/dashboard'])
        },
        error: () => {
          console.log("Siga mudar de pagina")
          this.showError = true;
        }
      })

    }
  }

}