import { UserService } from './user.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';
import { Category } from './../interfaces/Category';
import { AuthenticationService } from './authentication.service';
import { Store } from '../interfaces/Store';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private authService: AuthenticationService,private http: HttpClient, private userService:UserService) {
    let email = localStorage.getItem('userEmail');
    let dtype = localStorage.getItem('dtype');
    let loja = localStorage.getItem('store');
    let userFullName = localStorage.getItem('userFullName');
    if (email != null && dtype != null && dtype === "Client" && userFullName != null) {
      this.userService.setClient({ "email": email, fullname: userFullName})
    } else if (email != null && dtype != null && dtype === "Manager" && loja != null && userFullName != null) {
          this.userService.setManager({ "email": email, store: this.forceCast<Store>(loja), fullname: userFullName })
    }
  }

  forceCast<Store>(input: any): Store {
    return input;
  }

  getAll() {
    return this.http.get<Category[]>(environment.baseAPIPath + "/categories")
  }


  createCategory(name: string) {
    this.http.post<Category>(environment.baseAPIPath  + "/categories/add", { "name": name, "isActive": true }, httpOptions).subscribe(response => console.log(response))
  }
}
