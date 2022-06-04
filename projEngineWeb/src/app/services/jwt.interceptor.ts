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

    //if (isLoggedIn && isApiUrl) {
      const ls = localStorage.getItem('token');
      const token = ls?.split(':')[1];
      console.log("aqqqiiiii: " + token);
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbmRyZUB1YS5wdCIsImV4cCI6MTY1NDM2MjUzMywiaWF0IjoxNjU0MzU1MzMzfQ.bPAR8D4UYEj30z2UZbeLOE4eIdbzBA5tUEs5kdnS0yVg0Glo-7DrhEGKlPKefi2v8nyAwMNgAF1VvDpmX5nJ5w`
        }
      });
    //}

    return next.handle(request);
  }
}