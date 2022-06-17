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

  private baseUrl = "http://localhost:8080/categories";

  constructor(private http: HttpClient, private userService:UserService) {
    let email = localStorage.getItem('userEmail');
    if (email != null) {
      this.http.get<any>(environment.baseAPIPath + '/users/byEmail/' + email).subscribe(
        (res) => {
          console.log(res)
          if (res.hasOwnProperty('cart')) {
            this.userService.setClient(res);
          } else if (res.hasOwnProperty('store')) {
            this.userService.setManager(res);
          }
        }
      );
    }
  }

  getCategory(id: number): Observable<Category> {
    const url = this.baseUrl + '/' + id;
    return this.http.get<Category>(url);
  }


  getAll() {
    return this.http.get<Category[]>(this.baseUrl + '/categories')
  }


  createCategory(name: string) {
    this.http.post<Category>(this.baseUrl + "/add", { "name": name, "isActive": true }, httpOptions).subscribe(response => console.log(response))
  }
}
