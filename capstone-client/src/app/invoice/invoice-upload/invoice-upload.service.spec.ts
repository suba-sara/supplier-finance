import { TestBed } from '@angular/core/testing';

import { InvoiceUploadService } from './invoice-upload.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('InvoiceService', () => {
  let service: InvoiceUploadService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(InvoiceUploadService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
