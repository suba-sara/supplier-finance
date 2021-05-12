import { TestBed } from '@angular/core/testing';

import { UnauthorizedInterceptor } from './unauthorized.interceptor';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';

describe('UnauthorizedInterceptorInterceptor', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
      providers: [UnauthorizedInterceptor],
    })
  );

  it('should be created', () => {
    const interceptor: UnauthorizedInterceptor = TestBed.inject(
      UnauthorizedInterceptor
    );
    expect(interceptor).toBeTruthy();
  });
});
