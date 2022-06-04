import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { AuthenticationService } from './authentication.service';

@Injectable()
export class RequestInterceptor implements HttpInterceptor {

  constructor(private authenticationService : AuthenticationService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const user = this.authenticationService.currentUserValue;
    const isLoggedIn = user && user.token;
    const isApiUrl = request.url.startsWith("http://localhost:8000");

    if (isLoggedIn && isApiUrl) {
      request.headers.set('Authorization', `Bearer ${user.token}`)
    }

    return next.handle(request);
  }
}