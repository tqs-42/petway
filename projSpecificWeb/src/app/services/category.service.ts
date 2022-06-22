import { UserService } from './user.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';
import { Category } from './../interfaces/Category';
import { AuthenticationService } from './authentication.service';

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
    if (email != null && dtype != null && dtype === "Client") {
      this.userService.setClient({ "email": email, fullname: ''})
    } else if (email != null && dtype != null && dtype === "Manager") {
      this.authService.getStoreFromManager(email).subscribe(loja =>{
        console.log(loja)
        if (email != null) {
          this.userService.setManager({ "email": email, store: loja, fullname: '' })
        }
      })
    }
  }

  getAll() {
    return this.http.get<Category[]>(environment.baseAPIPath + "/categories")
  }


  createCategory(name: string) {
    this.http.post<Category>(environment.baseAPIPath  + "/categories/add", { "name": name, "isActive": true }, httpOptions).subscribe(response => console.log(response))
  }
}
