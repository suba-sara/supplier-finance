import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-invoice-upload',
  templateUrl: './invoice-upload.component.html',
  styleUrls: ['./invoice-upload.component.scss'],
})
export class InvoiceUploadComponent implements OnInit {
  clientId = '';

  uploadInvoiceForm = new FormGroup({
    supplierId: new FormControl(''),
    invoiceNumber: new FormControl(''),
    invoiceDate: new FormControl(''),
    invoiceTitle: new FormControl(''),
    invoiceAmount: new FormControl(''),
    currency: new FormControl(''),
    invoicePayment: new FormControl(''),
    invoiceFile: new FormControl(''),
  });

  constructor() {}

  ngOnInit(): void {
    this.clientId = 'cl1001';
  }
}
