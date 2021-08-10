import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ViewInvoicesService } from '../view-invoices/view-invoices.service';
import { InvoiceInfoService } from './invoice-info.service';


@Component({
  selector: 'app-invoice-info',
  templateUrl: './invoice-info.component.html',
  styleUrls: ['./invoice-info.component.scss']
})
export class InvoiceInfoComponent implements OnInit {
  numberOfInvoice:any;
  uploadedCount:any;
  inReviewCount:any;
  approvedCount:any;
  rejectedCount:any;
  constructor(private invoiceInfoService:InvoiceInfoService) { }

  ngOnInit(): void {
    this.getInvoiceInfo();

  }

getInvoiceInfo(){
  this.invoiceInfoService.getInvoiceDetails().subscribe(res => {
    console.log(res);
   this.numberOfInvoice = res;
   this.uploadedCount = this.numberOfInvoice.uploadedCount;
   this.inReviewCount = this.numberOfInvoice.inReviewCount;
   this.approvedCount = this.numberOfInvoice.approvedCount;
   this.rejectedCount = this.numberOfInvoice.rejectedCount;
  })
}

 

}
