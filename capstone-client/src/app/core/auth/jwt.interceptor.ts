import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor() {
  }

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    // get access token from local storage
    const accessToken = localStorage.getItem('access_token');
    // check if the request is an API request or not
    const isApiUrl = request.url.startsWith(environment.API_PATH);

    // set authorization header if accessToken is available and request is a API request
    if (accessToken && isApiUrl) {
      request = request.clone({
        setHeaders: {Authorization: `Bearer ${accessToken}`},
      });
    }
    return next.handle(request);
  }
}
