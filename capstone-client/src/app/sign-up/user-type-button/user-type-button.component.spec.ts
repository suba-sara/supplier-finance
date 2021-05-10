import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserTypeButtonComponent } from './user-type-button.component';

describe('UserTypeFormComponent', () => {
  let component: UserTypeButtonComponent;
  let fixture: ComponentFixture<UserTypeButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserTypeButtonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserTypeButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
