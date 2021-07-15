import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Invoice } from '../invoice.types';
import { HttpClient } from '@angular/common/http';
import { InvoicePageType } from '../invoice.page.type';
import { environment } from '../../../environments/environment';

const { API_PATH } = environment;

export type InvoiceFiltersOptional = {
  pageSize?: number;
  pageIndex?: number;
  invoiceNumber?: string;
  supplierCode?: string;
  dateFrom?: Date;
  dateTo?: Date;
  ageing?: number;
  status?: string;
};

export type dataSD = {
  clientId: string;
};

export type InvoiceFilters = InvoiceFiltersOptional & {
  pageSize: number;
  pageIndex: number;
};

@Injectable({
  providedIn: 'root',
})
export class ViewInvoicesService {
  $dataSD: dataSD = {
    clientId: 'client',
  };
  $filters = new BehaviorSubject<InvoiceFilters>({
    pageSize: 10,
    pageIndex: 0,
  });

  $data = new BehaviorSubject<Invoice[]>([]);

  constructor(private http: HttpClient) {
    this.http
      .get<InvoicePageType>(`${API_PATH}/invoices/retrieve/bank`)
      .subscribe((inData: InvoicePageType) => {
        console.log(inData.content);
        this.$data.next(inData.content);
      });
  }
}
