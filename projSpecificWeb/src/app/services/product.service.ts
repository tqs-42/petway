import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';
import { Product } from './../interfaces/Product';


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrl = "http://localhost:8080/product";

  constructor(private http: HttpClient) { }

  getProducts(): Observable<Product[]> {
    const url = this.baseUrl;
    return this.http.get<Product[]>(url);
  }

  getAllSorting(sort:string){
    return this.http.get<Product[]>(environment.baseAPIPath + '/product/?ordering=' + sort)
  }

  getAll(name = "" , category = "") {
    return this.http.get<Product[]>(environment.baseAPIPath + '/product', {params: {search: name, category}})
  }

  getOne(id: number) {
    return this.http.get<Product>(environment.baseAPIPath + '/product/' + id)
  }

  createProduct(category: string, name: string, description: string, price: number, store: number, stock: number, image: string) {

    console.log("PARAMETROS PASSADOS --- ")
    console.log(category)
    console.log(name)
    console.log(description)
    console.log(price)
    console.log(stock)
    console.log(store)
    console.log(image)

    this.http.post<Product>(this.baseUrl + "/add-product", {  "category": category, "name": name, "description": description, "store": store, "stock": stock , "price": price, "image": image,"isActive": true}, httpOptions).subscribe(response => console.log("sou a resposta --- " + response))
  }

  updateProduct(product:Product) {
    this.http.put<Product>(this.baseUrl + "/"+ product.id, { "id": product.id, "name": product.name, "description": product.description, "price": product.price, "isActive": product.isActive, "category": product.category.id }, httpOptions).subscribe(response => console.log(response))
  }

}
