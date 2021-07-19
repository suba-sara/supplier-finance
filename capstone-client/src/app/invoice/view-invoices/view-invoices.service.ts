import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Invoice } from '../invoice.types';
import { HttpClient } from '@angular/common/http';
import { InvoicePageType } from '../invoice.page.type';
import { environment } from '../../../environments/environment';
import { AuthService } from '../../core/auth/auth.service';

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
  userTypeApiPath?: string;

  $dataSD: dataSD = {
    clientId: 'client',
  };
  $filters = new BehaviorSubject<InvoiceFilters>({
    pageSize: 10,
    pageIndex: 0,
  });

  $data = new BehaviorSubject<Invoice[]>([]);

  constructor(private authService: AuthService, private http: HttpClient) {
    authService.user$.subscribe((user) => {
      console.log(user);
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
          case 'BANK':
            this.userTypeApiPath = 'bank';
            break;
          default:
            this.userTypeApiPath = undefined;
        }
      }
    });

    this.http
      .get<InvoicePageType>(`${API_PATH}/invoices/retrieve/client`)
      .subscribe((inData: InvoicePageType) => {
        console.log(inData.content);
        this.$data.next(inData.content);
      });
  }
}
