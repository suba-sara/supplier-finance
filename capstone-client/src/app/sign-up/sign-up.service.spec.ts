import { TestBed } from '@angular/core/testing';

import { SignUpService } from './sign-up.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('SignUpService', () => {
  let service: SignUpService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(SignUpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
