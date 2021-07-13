import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewSingleInvoiceComponent } from './view-single-invoice.component';

describe('ViewSingleInvoiceComponent', () => {
  let component: ViewSingleInvoiceComponent;
  let fixture: ComponentFixture<ViewSingleInvoiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewSingleInvoiceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewSingleInvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
