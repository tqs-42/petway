import { HttpHeaders } from '@angular/common/http';
export const environment = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
  baseURL: 'http://172.17.0.1:8080',
  production: true
};