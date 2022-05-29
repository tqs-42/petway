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

  private baseUrl = environment.baseAPIPath + '/category';

  constructor(private http: HttpClient) { }

  getCategory(id: number): Observable<Category> {
    const url = this.baseUrl + '/' + id;
    return this.http.get<Category>(url);
  }


  getAll() {
    return this.http.get<Category[]>(environment.baseAPIPath + '/category')
  }


  createCategory(name: string) {
    this.http.post<Category>(this.baseUrl + "/", { "name":name, "isActive": true }, httpOptions).subscribe(response => console.log(response))
  }
}
