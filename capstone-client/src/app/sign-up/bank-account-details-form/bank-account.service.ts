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

  public getOTP(accountNumber: any): Observable<any> {
    return this.http.get(`${API_PATH}/account/get-otp/${accountNumber}`);
  }

  public verifyOTP(data: any): Promise<any> {
    return this.http.post(`${API_PATH}/account/verify`, data).toPromise();
  }
}
