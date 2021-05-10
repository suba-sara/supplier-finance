import { Component, OnInit } from '@angular/core';
import { PersonalDetails } from './personal-data-form/personal-data-form.component';

type SupplierAccDetail = {
  creditAccNumber?: number;
  bankCode?: number;
  supplierLimit?: number;
  invoicePayment?: string;
};

@Component({
  selector: 'app-sign-up-supplier',
  templateUrl: './sign-up-supplier.component.html',
  styleUrls: ['./sign-up-supplier.component.scss'],
})
export class SignUpSupplierComponent implements OnInit {
  personalDetails: PersonalDetails;
  pageNumber: number;

  supplierAccDetail: SupplierAccDetail;

  constructor() {
    this.personalDetails = {
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
  }

  ngOnInit(): void {}

  submitPersonalData = (value: PersonalDetails): void => {
    this.personalDetails = value;
    this.goToNextPage();
  };

  gotoPreviousPage = (): void => {
    if (this.pageNumber > 1) {
      this.pageNumber = this.pageNumber - 1;
    }
  };

  goToNextPage = (): void => {
    if (this.pageNumber < 3) {
      this.pageNumber = this.pageNumber + 1;
    }
  };
}
