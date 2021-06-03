import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ClientBankAccDetailsComponent} from './client-bank-acc-details.component';
import {CommonModule} from '@angular/common';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

describe('ClientBankAccDetailsComponent', () => {
  let component: ClientBankAccDetailsComponent;
  let fixture: ComponentFixture<ClientBankAccDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ClientBankAccDetailsComponent],
      imports: [
        CommonModule,
        RouterTestingModule,
        MatInputModule,
        MatButtonModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule
      ],
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
