import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RateMentorComponent } from './rate-mentor.component';

describe('RateMentorComponent', () => {
  let component: RateMentorComponent;
  let fixture: ComponentFixture<RateMentorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RateMentorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RateMentorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
