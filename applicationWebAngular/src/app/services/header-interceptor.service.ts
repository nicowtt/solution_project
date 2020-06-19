
import { Injectable } from "@angular/core";
import { HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { AuthService } from "./auth.service";

@Injectable()
export class HeaderInterceptorService implements HttpInterceptor {

  constructor(private authService: AuthService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    // Get the auth token from localStorage
    let bearer = 'Bearer ';
    let authToken = localStorage.getItem('currentUserToken');

    if (authToken && this.authService.currentUserValue) {
      let cleanToken = authToken.replace(/"/g, '');
      let bearerAuthToken = bearer.concat(cleanToken);
      const authReq = req.clone({
        setHeaders: {
          Authorization: bearerAuthToken
        }
      });
      return next.handle(authReq);
    }

    // send http without auth header
    console.log('passage interceptor without auth header');
    return next.handle(req);
  }
}
