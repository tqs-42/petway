import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { OrderComponent } from './pages/order/order.component';
import { OrdersComponent } from './pages/orders/orders.component';
import { RegisterComponent } from './pages/register/register.component';
import { RiderComponent } from './pages/rider/rider.component';
import { RidersComponent } from './pages/riders/riders.component';
import { StoreComponent } from './pages/store/store.component';
import { StoresComponent } from './pages/stores/stores.component';

const routes: Routes = [

  { path: '', component: OrdersComponent },

  { path: 'login', component: LoginComponent },

  { path: 'register', component: RegisterComponent },

  {
    path: 'orders', children: [
      { path: '', component: OrdersComponent },
      { path: ':id', component: OrderComponent }
    ]
  },

  {
    path: 'stores', children: [
      { path: '', component: StoresComponent },
      { path: ':id', component: StoreComponent }
    ]
  },

  {
    path: 'riders', children: [
      { path: '', component: RidersComponent },
      { path: ':id', component: RiderComponent }
    ]
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
