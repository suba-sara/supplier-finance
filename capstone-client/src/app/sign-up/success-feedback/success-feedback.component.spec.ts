import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuccessFeedbackComponent } from './success-feedback.component';

describe('SuccessFeedbackComponent', () => {
  let component: SuccessFeedbackComponent;
  let fixture: ComponentFixture<SuccessFeedbackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SuccessFeedbackComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SuccessFeedbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
