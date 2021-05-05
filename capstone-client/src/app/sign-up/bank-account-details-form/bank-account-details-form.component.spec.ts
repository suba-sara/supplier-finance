import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BankAccountDetailsFormComponent } from './bank-account-details-form.component';

describe('BankAccountDetailsFormComponent', () => {
  let component: BankAccountDetailsFormComponent;
  let fixture: ComponentFixture<BankAccountDetailsFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BankAccountDetailsFormComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BankAccountDetailsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
