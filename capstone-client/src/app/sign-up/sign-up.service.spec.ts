import { TestBed } from '@angular/core/testing';

import { SignUpService } from './sign-up.service';
import { HttpClientModule } from '@angular/common/http';

describe('SignUpService', () => {
  let service: SignUpService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    service = TestBed.inject(SignUpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
