import { Component, OnInit } from '@angular/core';
import { PersonalDetails } from './personal-data-form/personal-data-form.component';
import { UserDetails } from './user-data-form/user-data-form.component';
import { SignUpService } from './sign-up.service';
import { AppService } from '../app.service';
import { AccountDetails } from './bank-account-details-form/bank-account-details-form.component';

@Component({
  selector: 'app-sign-up-supplier',
  templateUrl: './sign-up-supplier.component.html',
  styleUrls: ['./sign-up-supplier.component.scss'],
})
export class SignUpSupplierComponent implements OnInit {
  personalDetails: PersonalDetails;
  pageNumber: number;
  accountDetails: AccountDetails;
  userDetails: UserDetails;

  constructor(
    private signUpService: SignUpService,
    private appService: AppService
  ) {
    this.personalDetails = {
      firstName: '',
      lastName: '',
      email: '',
      phone: '',
      addressLine1: '',
      city: '',
      state: '',
      province: '',
      country: '',
    };
    this.pageNumber = 1;

    this.accountDetails = {
      otp: '',
      accountNumber: '',
    };
    this.userDetails = { password: '', userId: '' };
  }

  ngOnInit(): void {
    this.appService.setPageTitle('Sign Up Supplier');
  }

  submitUserData = (value: UserDetails): void => {
    this.userDetails = value;
    this.goToNextPage();
  };
  submitPersonalData = (value: PersonalDetails): void => {
    this.personalDetails = value;
    this.goToNextPage();
  };
  submitAccountData = (value: AccountDetails): void => {
    this.accountDetails = value;
    this.register();
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

  register = (): void => {
    this.signUpService
      .signUpSupplier({
        ...this.userDetails,
        ...this.personalDetails,
        ...this.accountDetails,
      })
      .subscribe(() => {
        this.pageNumber = 4;
      });
  };
}
