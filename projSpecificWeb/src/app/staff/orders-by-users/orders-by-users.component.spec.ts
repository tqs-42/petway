import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdersByUsersComponent } from './orders-by-users.component';

describe('OrdersByUsersComponent', () => {
  let component: OrdersByUsersComponent;
  let fixture: ComponentFixture<OrdersByUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrdersByUsersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrdersByUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
