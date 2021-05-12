import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TitlePanelComponent } from './title-panel.component';
import { CommonModule } from '@angular/common';
import { RouterTestingModule } from '@angular/router/testing';

describe('TitlePanelComponent', () => {
  let component: TitlePanelComponent;
  let fixture: ComponentFixture<TitlePanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommonModule, RouterTestingModule],
      declarations: [TitlePanelComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TitlePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
