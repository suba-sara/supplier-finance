import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  Router,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    // unauthorized route check
    const isUnauthorizedOnly =
      route.data.roles.length === 1 && route.data.roles[0] === 'UNAUTHORIZED';

    const redirectRoute = isUnauthorizedOnly ? '/dashboard' : '/sign-in';

    return this.authService.user$.pipe(
      map((user) => {
        if (user && !isUnauthorizedOnly) {
          return true;
        } else if (!user && isUnauthorizedOnly) {
          return true;
        } else {
          this.router.navigate([redirectRoute]);
          return false;
        }
      })
    );
  }
}
