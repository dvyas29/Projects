import { TestBed } from '@angular/core/testing';

import { RateAppserviceService } from './rate-appservice.service';

describe('RateAppserviceService', () => {
  let service: RateAppserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RateAppserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
