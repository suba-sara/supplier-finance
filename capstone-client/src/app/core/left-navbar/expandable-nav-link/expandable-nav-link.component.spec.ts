import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpandableNavLinkComponent } from './expandable-nav-link.component';

describe('ExpandableNavLinkComponent', () => {
  let component: ExpandableNavLinkComponent;
  let fixture: ComponentFixture<ExpandableNavLinkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ExpandableNavLinkComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExpandableNavLinkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
