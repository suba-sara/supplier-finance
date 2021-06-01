import { Component, OnInit } from '@angular/core';
import { Invoice } from '../invoice.types';

const fakeData: Invoice[] = [
  {
    invoiceId: 13,
    invoiceDate: new Date(),
    invoiceNumber: 12131,
    supplierId: 'aasd11',
    clientId: 'wfass',
    amount: 200,
    currencyType: 'EUR',
    status: 'pending',
  },
  {
    invoiceId: 14,
    invoiceDate: new Date(),
    invoiceNumber: 1222131,
    supplierId: 'aaaasd11',
    clientId: 'wferass',
    amount: 200,
    currencyType: 'EUR',
    status: 'pending',
  },
  {
    invoiceId: 15,
    invoiceDate: new Date(),
    invoiceNumber: 12112431,
    supplierId: 'aasd11',
    clientId: 'wfass',
    amount: 200,
    currencyType: 'EUR',
    status: 'pending',
  },
  {
    invoiceId: 16,
    invoiceDate: new Date(),
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
  constructor() {}

  ngOnInit(): void {}
}
