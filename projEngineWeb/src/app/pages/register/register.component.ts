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
  form!: FormGroup;

  registerForm !: FormGroup;
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
    
    let error = false;

    if (this.registerForm.value.password != this.registerForm.value.password_repeat) {
      this.showConfirmPasswordError = true;
      error = true;
    }

    if (this.registerForm.invalid) {
      this.showError = true;
      error = true;
    }

    if (!error) {
      this.authenticationService.register(this.registerForm).subscribe({
        next: () => {
          this.router.navigate(['/'])
        },
        error: () => {
          this.showError = true;
        }
      })

    }
  }

}


