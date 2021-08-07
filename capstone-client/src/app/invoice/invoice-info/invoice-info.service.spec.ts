import { TestBed } from '@angular/core/testing';

import { InvoiceInfoService } from './invoice-info.service';

describe('InvoiceInfoService', () => {
  let service: InvoiceInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InvoiceInfoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
