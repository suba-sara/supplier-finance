import { TestBed } from '@angular/core/testing';

import { ViewSingleInvoicesService } from './view-single-invoice.service';

describe('ViewSingleInvoicesService', () => {
  let service: ViewSingleInvoicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ViewSingleInvoicesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
