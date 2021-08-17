import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { BankAccountService } from './bank-account.service';

export type AccountDetails = {
  accountNumber: string;
  otp: string;
};

@Component({
  selector: 'app-bank-account-details-form',
  templateUrl: './bank-account-details-form.component.html',
  styleUrls: ['./bank-account-details-form.component.scss'],
})
export class BankAccountDetailsFormComponent implements OnInit {
  @Input()
  goBack!: () => void;

  @Input()
  initialValues!: AccountDetails;

  @Output()
  formSubmitEvent = new EventEmitter<AccountDetails>();

  getOtpLoading = false;

  accountForm: FormGroup = new FormGroup({
    accountNumber: new FormControl('', [
      Validators.required,
      Validators.maxLength(8),
      Validators.minLength(8),
      Validators.pattern(/[\d]{8}/),
    ]),
  });

  isAccountChecked = false;
  isAccountVerify = false;
  errorMessage = '';

  constructor(private bankAccountService: BankAccountService) {}

  ngOnInit(): void {
    if (this.initialValues) {
      this.accountForm.controls['accountNumber'].setValue(
        this.initialValues.accountNumber
      );
    }
  }

  signUp(): void {
    // check verification
    this.bankAccountService.verifyOTP(this.accountForm.value).subscribe(
      (val) => {
        if (val) {
          this.formSubmitEvent.emit(this.accountForm.value);
        } else {
          this.errorMessage = 'Invalid Verification Code';
        }
      },
      (error) => (this.errorMessage = 'Invalid Verification Code')
    );
  }

  get accountNumber(): AbstractControl | null {
    return this.accountForm.get('accountNumber');
  }

  get otp(): AbstractControl | null {
    return this.accountForm.get('otp');
  }

  getOTP(): void {
    this.getOtpLoading = true;
    this.accountForm?.addControl(
      'otp',
      new FormControl(undefined, [
        Validators.required,
        Validators.maxLength(6),
        Validators.minLength(6),
        Validators.pattern(/[\d]{6}/),
      ])
    );
    this.bankAccountService
      .getOTP(this.accountForm?.controls['accountNumber'].value)
      .subscribe(
        () => {
          this.isAccountChecked = true;
          this.getOtpLoading = false;
        },
        (err) => {
          this.errorMessage = err === 'OK' ? 'INTERNAL SERVER ERROR' : err;
          this.getOtpLoading = false;
        }
      );
  }
}
