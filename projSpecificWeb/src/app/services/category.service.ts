import { UserService } from './user.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';
import { Category } from './../interfaces/Category';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient, private userService:UserService) {
    let email = localStorage.getItem('userEmail');
    let dtype = localStorage.getItem('dtype');
    if (email != null && dtype != null && dtype === "Client") {
      this.userService.setClient({ "email": email, address: '', fullname: ''})
    } else if (email != null && dtype != null && dtype === "Manager") {
      this.userService.setManager({ "email": email, store: { id: 0, name: '', address : '', active: true}, fullname: '' })
    }
  }

  getAll() {
    return this.http.get<Category[]>(environment.baseAPIPath + "/categories")
  }


  createCategory(name: string) {
    this.http.post<Category>(environment.baseAPIPath  + "/categories/add", { "name": name, "isActive": true }, httpOptions).subscribe(response => console.log(response))
  }
}
