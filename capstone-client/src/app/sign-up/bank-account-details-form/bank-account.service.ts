import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const { API_PATH } = environment;

@Injectable({
  providedIn: 'root',
})
export class BankAccountService {
  constructor(private http: HttpClient) {}

  public getOTP(accountNumber: number): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`${API_PATH}/account/get-otp/`, {
      accountNumber,
    });
  }

  public verifyOTP(data: any): Observable<{ valid: boolean }> {
    return this.http.post<{ valid: boolean }>(
      `${API_PATH}/account/check-otp`,
      data
    );
  }
}
