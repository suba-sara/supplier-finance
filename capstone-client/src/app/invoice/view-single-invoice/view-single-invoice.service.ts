import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Invoice, InvoiceStatus } from '../invoice.types';
import { Observable } from 'rxjs';

const { API_PATH } = environment;

@Injectable({
  providedIn: 'root',
})
export class ViewSingleInvoicesService {
  constructor(private http: HttpClient) {}

  public getInvoiceById(invoiceId: number): Promise<Invoice> {
    return this.http
      .get<Invoice>(`${API_PATH}/invoices/getById/${invoiceId}`)
      .toPromise();
  }

  updateStatus({
    invoiceId,
    status,
  }: {
    invoiceId: number;
    status: InvoiceStatus;
  }): Observable<any> {
    return this.http.put(`${API_PATH}/invoices/update/status`, {
      invoiceId,
      status,
    });
  }

  requestReview({
    invoiceId,
    status,
  }: {
    invoiceId: number;
    status: InvoiceStatus;
  }): Observable<any> {
    return this.http.put(`${API_PATH}/invoices/request-review`, {
      invoiceId,
      status,
    });
  }
}
