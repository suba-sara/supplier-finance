import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

export type BankDataFormClient = {
  loanAccountNumber: string;
  bankAccountNumber: string;
  clientLimit: string;
  limitValidityFrom: string;
  limitValidityTo: string;
  rateOfInterest: string;
  creditPeriod: string;
};

@Component({
  selector: 'app-client-bank-acc-details',
  templateUrl: './client-bank-acc-details.component.html',
  styleUrls: ['./client-bank-acc-details.component.scss'],
})
export class ClientBankAccDetailsComponent implements OnInit {
  @Input()
  goBack!: () => void;

  BankDataFormClient= new FormGroup({
    loanAccountNumber: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    bankAccountNumber: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    clientLimit: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    limitValidityFrom: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    limitValidityTo: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    rateOfInterest: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    creditPeriod: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    
  });

  @Output()
  signUpEvent: EventEmitter<void> = new EventEmitter<void>();

  constructor() {}

  ngOnInit(): void {}

  signUp(): void {
    this.signUpEvent.emit();
  }
}
