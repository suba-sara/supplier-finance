import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

const { API_PATH } = environment;

export type userUpdatePassword = {
  userId: string;
  password: string;
  OTP: string;
};

@Injectable({
  providedIn: 'root',
})
export class ForgotPasswordService {
  constructor(private http: HttpClient) {}

  searchAccount(
    userId: string
  ): Observable<{
    valid: boolean;
    message?: string;
  }> {
    return this.http.post<{
      valid: boolean;
      message?: string;
    }>(`${API_PATH}/user/forgotPassword/getOTP`, userId);
  }

  verifyUser(data: userUpdatePassword): Observable<{ valid: boolean }> {
    return this.http.post<{ valid: boolean }>(
      `${API_PATH}/user/forgotPassword/verifyUser`,
      data
    );
  }
}
