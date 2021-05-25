import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

const { API_PATH } = environment;

export type Invoice = {
  supplierId: string;
  invoiceNumber: string;
  invoiceDate: string;
  invoiceTitle: string;
  invoiceAmount: string;
  currency: string;
  invoiceFile: string;
};

@Injectable({
  providedIn: 'root',
})
export class InvoiceUploadService {
  constructor(private http: HttpClient) {}

  checkSupplierId(supplierId: number): Observable<{ isValid: boolean }> {
    return this.http.get<{ isValid: boolean }>(
      `${API_PATH}/users/checkSupplierId?supplierId=${supplierId}`
    );
  }

  uploadInvoice(invoice: Invoice): Observable<Invoice> {
    const formData = new FormData();
    formData.append('supplierId', invoice.supplierId);
    formData.append('invoiceNumber', invoice.invoiceNumber);
    formData.append('invoiceDate', invoice.invoiceDate);
    formData.append('invoiceTitle', invoice.invoiceTitle);
    formData.append('invoiceAmount', invoice.invoiceAmount);
    formData.append('currency', invoice.currency);
    formData.append('invoiceFile', invoice.invoiceFile);

    return this.http.post<Invoice>(`${API_PATH}/invoices/create`, formData);
  }
}
