import { TestBed } from '@angular/core/testing';

import { UtilityserviceService } from './utilityservice.service';

describe('UtilityserviceService', () => {
  let service: UtilityserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UtilityserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
