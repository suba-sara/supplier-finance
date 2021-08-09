import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../core/auth/auth.service';

export type InvoiceStatusFilterType =
  | 'Uploaded'
  | 'InReview'
  | 'Completed'
  | 'Rejected'
  | 'ALL';

@Component({
  selector: 'app-invoice-status-filter',
  templateUrl: './invoice-status-filter.component.html',
  styleUrls: ['./invoice-status-filter.component.scss'],
})
export class InvoiceStatusFilterComponent implements OnInit {
  selectedStatusType: InvoiceStatusFilterType = 'InReview';

  constructor(public authService: AuthService) {}

  ngOnInit(): void {}

  setFilterType = (type: InvoiceStatusFilterType): void => {
    this.selectedStatusType = type;
  };
}
