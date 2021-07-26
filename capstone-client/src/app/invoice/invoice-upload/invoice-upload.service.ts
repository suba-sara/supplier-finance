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
  amount: string;
  currencyType: string;
};

@Injectable({
  providedIn: 'root',
})
export class InvoiceUploadService {
  constructor(private http: HttpClient) {}

  fetchMyClientId(): Observable<{ clientId: string }> {
    return this.http.get<{ clientId: string }>(`${API_PATH}/users/myClientId`);
  }

  checkSupplierId(supplierId: number): Observable<{ isValid: boolean }> {
    return this.http.get<{ isValid: boolean }>(
      `${API_PATH}/users/checkSupplierId?supplierId=${supplierId}`
    );
  }

  async createInvoice(
    invoice: Invoice & {
      invoiceFile: string;
      invoiceFileSrc: string;
    }
  ): Promise<any> {
    const { invoiceFile, invoiceFileSrc, ...rest } = invoice;
    const res = await this.http
      .post<{ fileId: string; token: string; invoice: Invoice }>(
        `${API_PATH}/invoices/create`,
        rest
      )
      .toPromise();

    const formData = new FormData();
    formData.append('token', res.token);
    formData.append('file', invoiceFileSrc);

    await this.http
      .post(`${API_PATH}/files/invoice/upload/${res.fileId}`, formData)
      .toPromise()
      .catch(() => {
      });

    return res;
  }
}
