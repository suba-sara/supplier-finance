import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ViewSingleInvoicesService } from './view-single-invoice.service';
import { Invoice } from '../invoice.types';

@Component({
  selector: 'app-view-single-invoice',
  templateUrl: './view-single-invoice.component.html',
  styleUrls: ['./view-single-invoice.component.scss'],
})
export class ViewSingleInvoiceComponent implements OnInit {
  dataSource: Invoice[] = [];
  displayedColumns: string[] = [
    'supplierId',
    'invoiceDate',
    'amount',
    'invoiceId',
    'invoiceNumber',
    'invoiceAge',
    'invoiceStatus',
    'options',
  ];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private viewSingleInvoicesService: ViewSingleInvoicesService
  ) {
    const invoiceId = this.route.snapshot.params['id'];
    this.viewSingleInvoicesService
      .getInvoiceById(invoiceId)
      ?.then((res) => (this.dataSource = res.content));
  }

  ngOnInit(): void {}
}
