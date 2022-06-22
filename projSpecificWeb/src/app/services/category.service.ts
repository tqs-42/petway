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
    let email = localStorage.getItem('email');
    if (email != null) {
      this.http.get<any>(environment.baseAPIPath + '/users/byEmail/' + email).subscribe(
        (res) => {
          if (res.hasOwnProperty('address')) {
            this.userService.setClient(res);
          } else if (res.hasOwnProperty('store')) {
            this.userService.setManager(res);
          }
        }
      );
    }
  }

  getAll() {
    return this.http.get<Category[]>(environment.baseAPIPath + "/categories")
  }


  createCategory(name: string) {
    this.http.post<Category>(environment.baseAPIPath  + "/categories/add", { "name": name, "isActive": true }, httpOptions).subscribe(response => console.log(response))
  }
}
