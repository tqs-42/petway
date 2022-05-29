import { NewStaffComponent } from './staff/new-staff/new-staff.component';
import { ShowProductComponent } from './staff/show-product/show-product.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './client/app/app.component';
import { AppStaffComponent } from './staff/app-staff/app-staff.component';
import { CategoriesComponent } from './staff/categories/categories.component';
import { DashboardComponent } from './staff/dashboard/dashboard.component';
import { ProductsComponent } from './staff/products/products.component';
import { ProductsComponent as ProdutsClient } from './client/products/products.component';
import { StaffAccountsComponent } from './staff/staff-accounts/staff-accounts.component';
import { UsersAccountsComponent } from './staff/users-accounts/users-accounts.component';
import { UsersOrdersComponent } from './staff/users-orders/users-orders.component';
import { SingleComponent } from './client/single/single.component';
import { ViewCartComponent } from './client/view-cart/view-cart.component';
import { OrdersCliComponent } from './client/orders-cli/orders-cli.component';
import { OrderDetailsComponent } from './client/order-details/order-details.component';
import { NewCategoryComponent } from './staff/new-category/new-category.component';
import { NewProductComponent } from './staff/new-product/new-product.component';
import { OrderDetailStaffComponent } from './staff/order-detail-staff/order-detail-staff.component';
import { OrdersByUsersComponent } from './staff/orders-by-users/orders-by-users.component';

const routes: Routes = [
  {
    path: '', component: AppComponent,
    children: [
      {path: '', component: ProdutsClient},
      {path: 'product', children: [
        {path: '', component: ProdutsClient},
        {path: ':id', component: SingleComponent}
      ]},
      {path: 'cart' , component: ViewCartComponent},
      {path: 'order', children: [
        {path: '', component: OrdersCliComponent},
        {path: ':id', component: OrderDetailsComponent}
      ]},
    ],
  },
  {
    path: 'system', component: AppStaffComponent,
    children: [
      {path: 'dashboard', component: DashboardComponent},
      {path: 'product' , children: [
        {path:'', component: ProductsComponent},
        {path:'new', component: NewProductComponent},
        {path:':id', component: SingleComponent},
      ]},
      {path: 'categories' , children: [
        {path: '', component: CategoriesComponent},
        {path: 'new' , component: NewCategoryComponent}
      ]},
      {path: 'orders' , children: [
        {path: '', component: UsersOrdersComponent},
        {path: ':id', component: OrderDetailsComponent},
      ]},
      {path: 'categories' , component: CategoriesComponent},
      {path: 'orders' , component: UsersOrdersComponent},
      {path: 'users' ,children: [
        {path: '' , component: UsersAccountsComponent},
        {path: ':id', component: OrdersByUsersComponent}
      ]},
      {path: 'staff' , children: [
        {path: '', component: StaffAccountsComponent},
        {path: 'new' , component: NewStaffComponent},
      ]},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
