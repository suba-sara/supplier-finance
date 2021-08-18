import { Injectable, OnDestroy } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

const { API_PATH } = environment;

export type UserType = 'CLIENT' | 'SUPPLIER' | 'BANKER';

export type SignInData = {
  userId: string;
  password: string;
};

export type SignInResponse = {
  jwt: string;
  username: string;
  userType: UserType;
};

export type ApplicationUser = {
  username: string;
  userType: UserType;
};

export type ProfileResponse = {
  username: string;
  userType: UserType;
  clientId?: string;
  supplierId?: string;
  employeeId?: string;
};

@Injectable({
  providedIn: 'root',
})
export class AuthService implements OnDestroy {
  /* to be used in future for refresh tokens */
  // private timer!: Subscription;

  // Declare variables
  user = new BehaviorSubject<ApplicationUser | null>(null);
  user$: Observable<ApplicationUser | null> = this.user.asObservable();

  /**
   * Method to storage event listener
   */
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

  /**
   * Constructor Method
   */
  constructor(private http: HttpClient) {
    window.addEventListener('storage', this.storageEventListener.bind(this));
  }

  ngOnDestroy(): void {
    window.removeEventListener('storage', this.storageEventListener.bind(this));
  }

  /**
   * Method to handle sign in
   *
   * @param data send data from sign-in component
   * @return SignInResponse observable
   */
  signIn(data: SignInData): Observable<SignInResponse> {
    return this.http.post<SignInResponse>(`${API_PATH}/sign-in`, data).pipe(
      map((response) => {
        // pass new user data to user stream
        this.user.next({
          username: response.username,
          userType: response.userType,
        });
        // call setLocalStorage method
        this.setLocalStorage({
          accessToken: response.jwt,
          userName: response.username,
          userType: response.userType,
        });
        // add a new login event so that other tabs will also get notified of the changed state
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

  /**
   * Method to set local storage data
   *
   * @param accessToken JWT token
   * @param userName Username
   * @param userType User Type
   */
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

  /**
   * Method to clear local storage
   */
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

  getProfile(): Observable<ProfileResponse> {
    return this.http.get<ProfileResponse>(`${API_PATH}/users/profile`);
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
