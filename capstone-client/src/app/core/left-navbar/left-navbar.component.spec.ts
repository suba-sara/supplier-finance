import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeftNavbarComponent } from './left-navbar.component';
import { CommonModule } from '@angular/common';
import { AuthModule } from '../auth/auth.module';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatRippleModule } from '@angular/material/core';
import { RouterTestingModule } from '@angular/router/testing';

describe('LeftNavbarComponent', () => {
  let component: LeftNavbarComponent;
  let fixture: ComponentFixture<LeftNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        CommonModule,
        AuthModule,
        MatIconModule,
        MatButtonModule,
        RouterTestingModule,
        MatTooltipModule,
        MatRippleModule,
      ],
      declarations: [LeftNavbarComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LeftNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
