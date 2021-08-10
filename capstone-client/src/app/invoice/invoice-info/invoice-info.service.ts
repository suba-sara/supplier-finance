import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../core/auth/auth.service';
import { environment } from 'src/environments/environment';

const { API_PATH } = environment;
@Injectable({
  providedIn: 'root'
})
export class InvoiceInfoService {
  userTypeApiPath?: string;
  userType:any;
  constructor(private http: HttpClient) { }
  
  getInvoiceDetails(){
    let url = `${API_PATH}/invoices/dashboard-data`
    return this.http.get(url);
  }
}
