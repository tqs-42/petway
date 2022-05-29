import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderDetailStaffComponent } from './order-detail-staff.component';

describe('OrderDetailStaffComponent', () => {
  let component: OrderDetailStaffComponent;
  let fixture: ComponentFixture<OrderDetailStaffComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderDetailStaffComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderDetailStaffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
