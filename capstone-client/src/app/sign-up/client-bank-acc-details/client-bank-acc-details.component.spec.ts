import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientBankAccDetailsComponent } from './client-bank-acc-details.component';

describe('ClientBankAccDetailsComponent', () => {
  let component: ClientBankAccDetailsComponent;
  let fixture: ComponentFixture<ClientBankAccDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ClientBankAccDetailsComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientBankAccDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
