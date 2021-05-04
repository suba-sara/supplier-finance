import { Component, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';

type ClientDetail = {
  firstName: string;
  lastName: string;
  email: string;
  mobileNumber: string;
  addressLine1: string;
  city: string;
  state: string;
  province: string;
  country: string;
};
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
  clientDetails: ClientDetail;
  pageNumber: number;

  clientFormData = new FormGroup(
    {
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      phone: new FormControl('', [
        Validators.required,
        Validators.pattern(/^[0-9+ ]+$/),
      ]),
      addressLine1: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required]),
      state: new FormControl('', [Validators.required]),
      province: new FormControl('', [Validators.required]),
      country: new FormControl('', [Validators.required]),
    },
    {
      validators: [
        // full name validation function
        (control): ValidationErrors | null => {
          const firstName: string = control.get('firstName')?.value;
          const lastName: string = control.get('lastName')?.value;

          if (
            firstName.length + lastName.length < 3 ||
            firstName.length + lastName.length > 30
          ) {
            return {
              fullName: true,
            };
          }

          return null;
        },
      ],
    }
  );

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

  ngOnInit(): void {}

  goToPreviousPage(): void {
    if (this.pageNumber > 1) {
      this.pageNumber = this.pageNumber - 1;
    }
  }

  goToNextPage(): void {
    if (this.pageNumber < 2) {
      this.pageNumber = this.pageNumber + 1;
    }
  }
}
