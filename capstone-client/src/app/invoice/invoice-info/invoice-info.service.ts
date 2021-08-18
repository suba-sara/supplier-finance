import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

const { API_PATH } = environment;

type DashboardData = {
  uploadedCount: number;
  inReviewCount: number;
  approvedCount: number;
  rejectedCount: number;
};

@Injectable({
  providedIn: 'root',
})
export class InvoiceInfoService {
  userTypeApiPath?: string;
  userType: any;

  constructor(private http: HttpClient) {}

  getInvoiceDetails(): Observable<DashboardData> {
    return this.http.get<DashboardData>(`${API_PATH}/invoices/dashboard-data`);
  }
}
