import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ViewInvoicesService } from '../view-invoices/view-invoices.service';
import { InvoiceInfoService } from './invoice-info.service';

@Component({
  selector: 'app-invoice-info',
  templateUrl: './invoice-info.component.html',
  styleUrls: ['./invoice-info.component.scss'],
})
export class InvoiceInfoComponent implements OnInit {
  numberOfInvoice: any;
  uploadedCount: any;
  inReviewCount: any;
  approvedCount: any;
  rejectedCount: any;

  constructor(private invoiceInfoService: InvoiceInfoService) {}

  ngOnInit(): void {
    this.getInvoiceInfo();
  }

  getInvoiceInfo(): void {
    this.invoiceInfoService.getInvoiceDetails().subscribe((res) => {
      this.uploadedCount = res.uploadedCount;
      this.inReviewCount = res.inReviewCount;
      this.approvedCount = res.approvedCount;
      this.rejectedCount = res.rejectedCount;
    });
  }
}
