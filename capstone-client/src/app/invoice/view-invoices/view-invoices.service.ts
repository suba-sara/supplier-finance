import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Invoice, InvoiceStatus } from '../invoice.types';
import { HttpClient } from '@angular/common/http';
import { InvoicePageType } from '../invoice.page.type';
import { environment } from '../../../environments/environment';
import { AuthService } from '../../core/auth/auth.service';
import { skip } from 'rxjs/operators';

const { API_PATH } = environment;

export type InvoiceFiltersOptional = {
  pageSize?: number;
  pageIndex?: number;
  invoiceNumber?: string;
  supplierId?: string;
  clientId?: string;
  dateFrom?: Date;
  dateTo?: Date;
  ageing?: number;
  status?: InvoiceStatus;
  sortBy?: string;
  sortDirection?: string;
};

export type InvoiceFetchResults = {
  invoices: Invoice[];
  total: number;
};

export type InvoiceFilters = InvoiceFiltersOptional & {
  pageSize: number;
  pageIndex: number;
};

@Injectable({
  providedIn: 'root',
})
export class ViewInvoicesService {
  userTypeApiPath?: string;

  $filters = new BehaviorSubject<InvoiceFilters>({
    pageSize: 10,
    pageIndex: 0,
  });

  $data = new BehaviorSubject<InvoiceFetchResults>({ invoices: [], total: 0 });

  constructor(private authService: AuthService, private http: HttpClient) {
    authService.user$.subscribe((user) => {
      if (!user) {
        this.userTypeApiPath = undefined;
      } else {
        switch (user.userType) {
          case 'CLIENT':
            this.userTypeApiPath = 'client';
            break;
          case 'SUPPLIER':
            this.userTypeApiPath = 'supplier';
            break;
          case 'BANKER':
            this.userTypeApiPath = 'bank';
            break;
          default:
            this.userTypeApiPath = undefined;
        }
      }
    });

    /*
     fetch invoices on filter change
     initial value will be skipped because this will be updated from the
     ngOnInit function in the component
     */
    this.$filters.pipe(skip(1)).subscribe((filters) => {
      this.fetchInvoices(this.getParams(filters));
    });
  }

  getParams(filters: any): { [p: string]: string | string[] } {
    const params: { [p: string]: string | string[] } = {};
    Object.keys(filters).forEach((key) => {
      const value = filters[key as keyof InvoiceFilters];
      if (value || value === 0) {
        params[key] = value.toString();
      }
    });
    return params;
  }

  fetchInvoices(params: { [p: string]: string | string[] }): void {
    this.http
      .get<InvoicePageType>(
        `${API_PATH}/invoices/retrieve/${this.userTypeApiPath}`,
        {
          params,
        }
      )
      .subscribe((inData: InvoicePageType) => {
        this.$data.next({
          invoices: inData.content,
          total: inData.totalElements,
        });
      });
  }

  deleteInvoice(invoiceId: number): void {
    this.http
      .delete<{ invoiceId: number; status: string }>(
        `${API_PATH}/invoices/delete/${invoiceId}`
      )
      .subscribe((_res) => {
        this.fetchInvoices(this.getParams(this.$filters.value));
      });
  }
}
