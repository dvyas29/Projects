import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentWallDetailsComponent } from './student-wall-details.component';

describe('StudentWallDetailsComponent', () => {
  let component: StudentWallDetailsComponent;
  let fixture: ComponentFixture<StudentWallDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudentWallDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentWallDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
