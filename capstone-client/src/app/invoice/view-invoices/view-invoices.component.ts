import { Component, OnInit } from '@angular/core';
import { Invoice } from '../invoice.types';
import * as dayjs from 'dayjs';

const fakeData: Invoice[] = [
  {
    invoiceId: 13,
    invoiceDate: new Date('2021-11-23'),
    invoiceNumber: 12131,
    supplierId: 'aasd11',
    clientId: 'wfass',
    amount: 200,
    currencyType: 'EUR',
    status: 'pending',
  },
  {
    invoiceId: 14,
    invoiceDate: new Date('2021-07-23'),
    invoiceNumber: 1222131,
    supplierId: 'aaaasd11',
    clientId: 'wferass',
    amount: 200,
    currencyType: 'EUR',
    status: 'pending',
  },
  {
    invoiceId: 15,
    invoiceDate: new Date('2021-06-11'),
    invoiceNumber: 12112431,
    supplierId: 'aasd11',
    clientId: 'wfass',
    amount: 200,
    currencyType: 'EUR',
    status: 'pending',
  },
  {
    invoiceId: 16,
    invoiceDate: new Date('2023-05-30'),
    invoiceNumber: 12325131,
    supplierId: 'aasd11',
    clientId: 'wfass',
    amount: 200,
    currencyType: 'EUR',
    status: 'pending',
  },
  {
    invoiceId: 121,
    invoiceDate: new Date('2022-01-30'),
    invoiceNumber: 12325131,
    supplierId: 'aasd11',
    clientId: 'wfass',
    amount: 200,
    currencyType: 'EUR',
    status: 'pending',
  },
];

@Component({
  selector: 'app-view-invoices',
  templateUrl: './view-invoices.component.html',
  styleUrls: ['./view-invoices.component.scss'],
})
export class ViewInvoicesComponent implements OnInit {
  dataSource = fakeData;
  displayedColumns: string[] = [
    'supplierId',
    'invoiceDate',
    'amount',
    'invoiceNumber',
    'invoiceAge',
    'invoiceId',
  ];

  constructor() {}

  ngOnInit(): void {}

  formatDate(date: Date): string {
    return dayjs(date).format('DD-MMM-YYYY');
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
}
