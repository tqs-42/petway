import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StaffAccountsComponent } from './staff-accounts.component';

describe('StaffAccountsComponent', () => {
  let component: StaffAccountsComponent;
  let fixture: ComponentFixture<StaffAccountsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StaffAccountsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StaffAccountsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
