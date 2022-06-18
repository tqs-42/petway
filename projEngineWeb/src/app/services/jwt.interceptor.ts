import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { AuthenticationService } from './authentication.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private accountService: AuthenticationService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add auth header with jwt if user is logged in and request is to the api url
    const user = this.accountService.userValue;
    const isLoggedIn = user && user.token;
    const isApiUrl = request.url.startsWith(environment.baseURL);

    if (isLoggedIn && isApiUrl) {
      const ls = localStorage.getItem('token');
      const token = ls?.split(':')[1];
      const token_final =  token?.replace("\"", '').replace("\"", '').replace("}", '');
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token_final}`
        }
      });
    }

    return next.handle(request);
  }
}