import { HttpHeaders } from '@angular/common/http';
export const environment = {
  httpOptions: {
    headers: new HttpHeaders({'Content-Type': 'application/json', 'Access-Control-Allow-Origin': 'http://172.17.0.1:4200'})
  },
  baseURL: 'http://172.17.0.1:8080',
  production: true
};