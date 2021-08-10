import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

export type BankDataFormSupplier = {
  craditAccountNumber: string;
  bankCode: string;
  supplierLimit: string;
  invoicePayament: string;
};

@Component({
  selector: 'app-bank-account-details-form',
  templateUrl: './bank-account-details-form.component.html',
  styleUrls: ['./bank-account-details-form.component.scss'],
})
export class BankAccountDetailsFormComponent implements OnInit {
  @Input()
  goBack!: () => void;

  BankDataFormSupplier= new FormGroup({
    craditAccountNumber: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    bankCode: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    supplierLimit: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    invoicePayament: new FormControl('', [
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
