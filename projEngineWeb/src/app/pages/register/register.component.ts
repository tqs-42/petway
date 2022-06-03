import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm !: FormGroup;
  showPasswordError : Boolean = false;
  showConfirmPasswordError : Boolean = false;
  showError : Boolean = false;

  constructor(private fb : FormBuilder, private authenticationService : AuthenticationService, private router : Router) { }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      address: [null, [Validators.required]],
      fullname: [null, [Validators.required]],
      password:[null, [Validators.required, Validators.minLength(8)]],
      password_repeat:[null, [Validators.required, Validators.minLength(8)]]
    });
  }

  public submit(): void {

    this.showPasswordError = false;
    this.showConfirmPasswordError = false;
    
    let error = false;

    if (this.registerForm.value["password"].trim().length < 8) {
      error = true;
      this.showPasswordError = true;
    }

    if (this.registerForm.value["password_repeat"] != this.registerForm.value["password"]) {
      error = true;
      this.showConfirmPasswordError = true;
    }

    if (!error) {
      this.authenticationService.register(this.registerForm).subscribe((data) => {
        this.router.navigate(['/'])
      })

    }
  }

}


