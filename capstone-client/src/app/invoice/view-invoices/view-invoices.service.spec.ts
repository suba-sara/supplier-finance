import { TestBed } from '@angular/core/testing';

import { ViewInvoicesService } from './view-invoices.service';

describe('ViewInvoicesService', () => {
  let service: ViewInvoicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ViewInvoicesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
