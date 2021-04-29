import {Component, OnInit} from '@angular/core';

type ClientDetail = {
  firstName: string,
  lastName: string,
  email: string,
  mobileNumber: string,
  addressLine1: string,
  city: string,
  state: string,
  province: string;
  country: string,
};
type SupplierAccDetail = {
  creditAccNumber?: number,
  bankCode?: number,
  supplierLimit?: number,
  invoicePayment?: string

};

@Component({
  selector: 'app-sign-up-supplier',
  templateUrl: './sign-up-supplier.component.html',
  styleUrls: ['./sign-up-supplier.component.scss']
})
export class SignUpSupplierComponent implements OnInit {
  clientDetails: ClientDetail;
  pageNumber: 1 | 2;

  supplierAccDetail: SupplierAccDetail;

  constructor() {
    this.clientDetails = {
      firstName: '',
      lastName: '',
      email: '',
      mobileNumber: '',
      addressLine1: '',
      city: '',
      state: '',
      province: '',
      country: '',
    };
    this.pageNumber = 1;

    this.supplierAccDetail = {};
    this.pageNumber = 1;
  }

  ngOnInit(): void {
  }

  goToNextPage(): void {
    this.pageNumber = 2;
  }

  goToPreviousPage(): void {
    this.pageNumber = 1;
  }
}
