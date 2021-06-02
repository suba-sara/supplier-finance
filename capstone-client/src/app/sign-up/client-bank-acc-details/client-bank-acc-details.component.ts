import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-client-bank-acc-details',
  templateUrl: './client-bank-acc-details.component.html',
  styleUrls: ['./client-bank-acc-details.component.scss'],
})
export class ClientBankAccDetailsComponent implements OnInit {
  @Input()
  goBack!: () => void;

  constructor() {}

  ngOnInit(): void {}
}
