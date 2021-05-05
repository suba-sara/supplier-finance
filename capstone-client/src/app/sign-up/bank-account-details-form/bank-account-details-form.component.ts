import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-bank-account-details-form',
  templateUrl: './bank-account-details-form.component.html',
  styleUrls: ['./bank-account-details-form.component.scss'],
})
export class BankAccountDetailsFormComponent implements OnInit {
  @Output()
  goToPreviousPageEvent = new EventEmitter();

  goBack(): void {
    this.goToPreviousPageEvent.emit();
  }

  constructor() {}

  ngOnInit(): void {}
}
