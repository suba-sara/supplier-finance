import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignUpClientComponent } from './sign-up-client.component';
import { CommonModule } from '@angular/common';
import { RouterTestingModule } from '@angular/router/testing';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

describe('SignUpComponent', () => {
  let component: SignUpClientComponent;
  let fixture: ComponentFixture<SignUpClientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        CommonModule,
        RouterTestingModule,
        MatInputModule,
        MatButtonModule,
        FormsModule,
        ReactiveFormsModule,
      ],
      declarations: [SignUpClientComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SignUpClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
