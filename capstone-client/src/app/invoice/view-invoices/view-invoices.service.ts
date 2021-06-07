import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

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

export type InvoiceFilters = InvoiceFiltersOptional & {
  pageSize: number;
  pageIndex: number;
};

@Injectable({
  providedIn: 'root',
})
export class ViewInvoicesService {
  $filters = new BehaviorSubject<InvoiceFilters>({
    pageSize: 10,
    pageIndex: 0,
  });

  constructor() {}
}
