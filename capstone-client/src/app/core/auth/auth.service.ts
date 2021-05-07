import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

const { API_PATH } = environment;

export type SignInData = {
  username: string;
  password: string;
};

export type SignInResponse = {
  jwt: string;
  userType: string;
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  signIn(
    data: SignInData,
    handleResponse: (resData: { status: 200 | 403; message: string }) => void
  ): void {
    this.http.post<SignInResponse>(`${API_PATH}/sign-in`, data).subscribe(
      (response) => {
        // store data on local storage
        localStorage.setItem('token', response.jwt);
        localStorage.setItem('userType', response.userType);

        handleResponse({ status: 200, message: 'Successfully signed in' });
      },
      () => {
        handleResponse({
          status: 403,
          message: 'Invalid username or password',
        });
      }
    );
  }
}
