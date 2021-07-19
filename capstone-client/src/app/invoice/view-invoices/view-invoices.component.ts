import { Component, OnInit } from '@angular/core';
import { Invoice } from '../invoice.types';
import * as dayjs from 'dayjs';
import { Dayjs } from 'dayjs';
import { ActivatedRoute, Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import {
  InvoiceFiltersOptional,
  ViewInvoicesService,
} from './view-invoices.service';

@Component({
  selector: 'app-view-invoices',
  templateUrl: './view-invoices.component.html',
  styleUrls: ['./view-invoices.component.scss'],
})
export class ViewInvoicesComponent implements OnInit {
  dataSource: Invoice[] = [];
  displayedColumns: string[] = [
    'supplierId',
    'invoiceDate',
    'amount',
    'invoiceNumber',
    'invoiceAge',
    'invoiceStatus',
    'options',
  ];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    public viewInvoicesService: ViewInvoicesService
  ) {
    console.log(this.viewInvoicesService.userTypeApiPath);
    this.viewInvoicesService.$filters.subscribe((val) => console.log(val));
    this.viewInvoicesService.$data.subscribe((sd) => (this.dataSource = sd));
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      // assign parameters to state
      const pageSize = params['pageSize'] ? params['pageSize'] : 10;
      const pageIndex = params['pageNumber'] ? params['pageNumber'] - 1 : 0;
      const invoiceNumber = params['invoiceNumber'];
      const supplierCode = params['supplierCode'];
      const dateFrom = params['dateFrom']
        ? new Dayjs(params['dateFrom']).startOf('date').toDate()
        : undefined;
      const dateTo = params['dateTo']
        ? new Dayjs(params['dateTo']).startOf('date').toDate()
        : undefined;
      const ageing = params['ageing'];
      const status = params['status'];

      this.viewInvoicesService.$filters.next({
        pageSize,
        pageIndex,
        invoiceNumber,
        supplierCode,
        dateFrom,
        dateTo,
        ageing,
        status,
      });
    });
  }

  invoiceAge(date: Date): string {
    const formatOutput = (value: number, unit: string) =>
      value === 1 ? `${value} ${unit}` : `${value} ${unit}s`;

    const today = dayjs();

    const diffYears = dayjs(date).diff(today, 'years');
    if (diffYears) {
      return formatOutput(diffYears, 'year');
    }

    const diffMonths = dayjs(date).diff(today, 'months');
    if (diffMonths) {
      return formatOutput(diffMonths, 'month');
    }

    const diffDays = dayjs(date).diff(today, 'days');
    return formatOutput(diffDays, 'day');
  }

  filterInputChange = (e: any) => {
    const inputName = e.target?.name;
    const value = e.target?.value;

    this._changeQuery({
      [inputName]: !!value ? value : undefined,
    });
  };

  handlePageChange = (e: PageEvent) => {
    this._changeQuery({
      pageSize: e.pageSize,
      pageIndex: e.pageIndex,
    });
  };

  _changeQuery = (filers: InvoiceFiltersOptional) => {
    const queryParams: any = {
      ...this.viewInvoicesService.$filters.value,
      ...filers,
    };

    // adjust page number
    queryParams.pageNumber = queryParams.pageIndex + 1;
    delete queryParams.pageIndex;

    this.router.navigate([], {
      relativeTo: this.route,
      queryParams,
      queryParamsHandling: 'merge',
    });
  };
}
