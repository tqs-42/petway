import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs';
import { RegisterService } from "src/app/services/Register/register.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form!: FormGroup;

  constructor(private registerService: RegisterService, private formBuilder: FormBuilder,) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      fullname: ['', [Validators.required]],
      address: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(1)]]
  }); 
  }

  get f() {
    return this.form.controls;
  }

  saveNewRider(): void {

    this.registerService.register_rider(this.f.email.value, this.f.password.value, this.f.fullname.value, this.f.address.value).pipe(first()).subscribe(
      {
        next: (response) => console.log(response),
        error: (error) => console.log(error),
      }
    )
  }

}
