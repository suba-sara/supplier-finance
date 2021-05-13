import { Injectable, OnDestroy } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

const { API_PATH } = environment;

export type SignInData = {
  userId: string;
  password: string;
};

export type SignInResponse = {
  jwt: string;
  username: string;
  userType: string;
};

export type ApplicationUser = {
  username: string;
  userType: string;
};

@Injectable({
  providedIn: 'root',
})
export class AuthService implements OnDestroy {
  /* to be used in future for refresh tokens */
  // private timer!: Subscription;

  private user = new BehaviorSubject<ApplicationUser | null>(null);
  user$: Observable<ApplicationUser | null> = this.user.asObservable();

  private storageEventListener(event: StorageEvent): void {
    if (event.storageArea === localStorage) {
      if (event.key === 'logout-event') {
        this.user.next(null);
      }

      if (event.key === 'login-event') {
        location.reload();
      }
    }
  }

  constructor(private http: HttpClient) {
    window.addEventListener('storage', this.storageEventListener.bind(this));
  }

  ngOnDestroy(): void {
    window.removeEventListener('storage', this.storageEventListener.bind(this));
  }

  signIn(data: SignInData): Observable<SignInResponse> {
    return this.http.post<SignInResponse>(`${API_PATH}/sign-in`, data).pipe(
      map((response) => {
        this.user.next({
          username: response.username,
          userType: response.userType,
        });
        this.setLocalStorage({
          accessToken: response.jwt,
          userName: response.username,
          userType: response.userType,
        });
        localStorage.setItem('login-event', 'login ' + Date.now());
        /* to be used in future for refresh tokens */
        // this.startTokenTimer();

        return response;
      })
    );
  }

  signOut(): void {
    this.clearLocalStorage();
    this.user.next(null);
    localStorage.setItem('logout-event', 'logout ' + Date.now());
  }

  setLocalStorage({
    accessToken,
    userName,
    userType,
  }: {
    accessToken: string;
    userName: string;
    userType: string;
  }): void {
    // store data on local storage
    localStorage.setItem('access_token', accessToken);
    localStorage.setItem('username', userName);
    localStorage.setItem('user_type', userType);
  }

  clearLocalStorage(): void {
    localStorage.removeItem('access_token');
    localStorage.removeItem('username');
    localStorage.removeItem('user_type');
  }

  refreshToken(): Observable<void> {
    return this.http.post<SignInResponse>(`${API_PATH}/refresh-token`, {}).pipe(
      map((response) => {
        this.user.next({
          username: response.username,
          userType: response.userType,
        });
        this.setLocalStorage({
          accessToken: response.jwt,
          userName: response.username,
          userType: response.userType,
        });
      })
    );
  }

  /* to be used in future for refresh tokens */
  // private getTokenRemainingTime(): number {
  //   const accessToken = localStorage.getItem('access_token');
  //   if (!accessToken) {
  //     return 0;
  //   }
  //
  //   const jwtToken = JSON.parse(atob(accessToken.split('.')[1]));
  //   const expires = new Date(jwtToken.exp * 1000);
  //   return expires.getTime() - Date.now();
  // }

  // private startTokenTimer(): void {
  //   const timeout = this.getTokenRemainingTime();
  //
  //   this.timer = of(true)
  //     .pipe(
  //       delay(timeout),
  //       tap(() => this.refreshToken().subscribe())
  //     )
  //     .subscribe();
  // }

  // private stopTokenTime(): void {
  //   this.timer?.unsubscribe();
  // }
}
