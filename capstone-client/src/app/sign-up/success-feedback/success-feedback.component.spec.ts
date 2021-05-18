import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuccessFeedbackComponent } from './success-feedback.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('SuccessFeedbackComponent', () => {
  let component: SuccessFeedbackComponent;
  let fixture: ComponentFixture<SuccessFeedbackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SuccessFeedbackComponent],
      imports: [RouterTestingModule],
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
