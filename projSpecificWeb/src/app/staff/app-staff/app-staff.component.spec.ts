import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppStaffComponent } from './app-staff.component';

describe('AppStaffComponent', () => {
  let component: AppStaffComponent;
  let fixture: ComponentFixture<AppStaffComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppStaffComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppStaffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
