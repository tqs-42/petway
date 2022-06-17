import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';
import { Client } from '../interfaces/Client';
import { Manager } from '../interfaces/Manager';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  client: Client | null = null;
  manager: Manager | null = null;

  constructor(private router: Router, private http: HttpClient) {
  }

  setClient(client: Client) {
    this.client = client;
  }

  setManager(manager: Manager) {
    this.manager = manager;
  }

  logout() {
    this.client = null;
    this.manager = null;
    localStorage.removeItem('userEmail');
    this.router.navigate(['/']);
  }

}
