import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdersCliComponent } from './orders-cli.component';

describe('OrdersCliComponent', () => {
  let component: OrdersCliComponent;
  let fixture: ComponentFixture<OrdersCliComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrdersCliComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrdersCliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
