import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';


@Injectable({
  providedIn: 'root',
})
export class BankAccountService {
  API_PATH = environment;

  constructor(private httpClient: HttpClient) {
  }

  public checkAccountNumber(accountNumber: any): Promise<any> {
    return this.httpClient.get(`${this.API_PATH}/account/check${accountNumber}`).toPromise();
  }

  public verifyOTP(data: any): Promise<any> {
    return this.httpClient.post(`${this.API_PATH}/account/verified`, data).toPromise();
  }
}
