import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

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

  constructor() {}

  ngOnInit(): void {}

  signUp(): void {
    this.signUpEvent.emit();
  }
}
