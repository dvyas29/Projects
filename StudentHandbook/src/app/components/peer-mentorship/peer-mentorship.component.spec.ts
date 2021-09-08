import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeerMentorshipComponent } from './peer-mentorship.component';

describe('PeerMentorshipComponent', () => {
  let component: PeerMentorshipComponent;
  let fixture: ComponentFixture<PeerMentorshipComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PeerMentorshipComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PeerMentorshipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
