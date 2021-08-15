import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { BankAccountService } from './bank-account.service';

@Component({
  selector: 'app-bank-account-details-form',
  templateUrl: './bank-account-details-form.component.html',
  styleUrls: ['./bank-account-details-form.component.scss'],
})
export class BankAccountDetailsFormComponent implements OnInit {
  @Input()
  goBack!: () => void;

  @Output()
  signUpEvent: EventEmitter<void> = new EventEmitter<void>();

  accountForm: FormGroup = new FormGroup({
    accountNumber: new FormControl('', [
      Validators.required,
      Validators.maxLength(10),
      Validators.minLength(10),
      Validators.pattern(/^-?(0|[1-9]\d*)?$/),
    ]),
  });

  isAccountChecked = false;
  isAccountVerify = false;
  errorMessage = '';

  constructor(private bankAccountService: BankAccountService) {}

  ngOnInit(): void {}

  signUp(): void {
    this.signUpEvent.emit();
  }

  get accountNumber(): AbstractControl | null {
    return this.accountForm.get('accountNumber');
  }

  get OTP(): AbstractControl | null {
    return this.accountForm.get('OTP');
  }

  checkAccountNumber(): void {
    this.accountForm?.addControl(
      'OTP',
      new FormControl(undefined, [
        Validators.required,
        Validators.maxLength(6),
        Validators.minLength(6),
        Validators.pattern(/^-?(0|[1-9]\d*)?$/),
      ])
    );
    this.bankAccountService
      .checkAccountNumber(this.accountForm?.controls['accountNumber'].value)
      .subscribe(
        () => (this.isAccountChecked = true),
        (err) =>
          (this.errorMessage = err === 'OK' ? 'INTERNAL SERVER ERROR' : err)
      );
  }

  verifyOTP(): void {
    this.bankAccountService
      .verifyOTP(this.accountForm?.value)
      .then(() => (this.isAccountVerify = true))
      .catch((err) => (this.errorMessage = err));
  }
}
