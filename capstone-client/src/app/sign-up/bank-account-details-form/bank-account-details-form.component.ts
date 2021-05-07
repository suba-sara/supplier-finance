import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-bank-account-details-form',
  templateUrl: './bank-account-details-form.component.html',
  styleUrls: ['./bank-account-details-form.component.scss'],
})
export class BankAccountDetailsFormComponent implements OnInit {
  @Input()
  goBack!: () => void;

  constructor() {}

  ngOnInit(): void {}
}
