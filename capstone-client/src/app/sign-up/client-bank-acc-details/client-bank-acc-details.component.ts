import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-client-bank-acc-details',
  templateUrl: './client-bank-acc-details.component.html',
  styleUrls: ['./client-bank-acc-details.component.scss'],
})
export class ClientBankAccDetailsComponent implements OnInit {
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
