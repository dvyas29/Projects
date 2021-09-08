import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupFormationComponent } from './group-formation.component';

describe('GroupFormationComponent', () => {
  let component: GroupFormationComponent;
  let fixture: ComponentFixture<GroupFormationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupFormationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupFormationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
