import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeerMentorshipRegistarationComponent } from './peer-mentorship-registaration.component';

describe('PeerMentorshipRegistarationComponent', () => {
  let component: PeerMentorshipRegistrationComponent;
  let fixture: ComponentFixture<PeerMentorshipRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PeerMentorshipRegistrationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PeerMentorshipRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
