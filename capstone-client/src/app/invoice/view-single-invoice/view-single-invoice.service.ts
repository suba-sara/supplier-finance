import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Invoice } from '../invoice.types';

const { API_PATH } = environment;

@Injectable({
  providedIn: 'root',
})
export class ViewSingleInvoicesService {
  constructor(private http: HttpClient) {}

  public getInvoiceById(invoiceId: number): Promise<Invoice> {
    console.log(invoiceId);
    return this.http
      .get<Invoice>(`${API_PATH}/invoices/getById/${invoiceId}`)
      .toPromise();
  }
}
