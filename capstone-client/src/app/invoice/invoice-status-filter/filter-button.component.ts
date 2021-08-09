import { Component, Input, OnInit } from '@angular/core';
import { InvoiceStatusFilterType } from './invoice-status-filter.component';

@Component({
  selector: 'app-invoice-filter-button',
  templateUrl: 'filter-button.component.html',
  styleUrls: ['invoice-status-filter.component.scss'],
})
export class FilterButtonComponent implements OnInit {
  @Input()
  setFilterType!: (type: InvoiceStatusFilterType) => void;

  @Input()
  type!: InvoiceStatusFilterType;

  @Input()
  currentSelection!: InvoiceStatusFilterType;

  ngOnInit(): void {}
}
