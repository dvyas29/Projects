import { TestBed } from '@angular/core/testing';

import { RateMentorserviceService } from './rate-mentorservice.service';

describe('RateMentorserviceService', () => {
  let service: RateMentorserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RateMentorserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
