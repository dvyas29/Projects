import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequesterDetailsModalComponent } from './requester-details-modal.component';

describe('RequesterDetailsModalComponent', () => {
  let component: RequesterDetailsModalComponent;
  let fixture: ComponentFixture<RequesterDetailsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RequesterDetailsModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RequesterDetailsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
