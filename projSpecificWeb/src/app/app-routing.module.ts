import { ShowProductComponent } from './staff/show-product/show-product.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './client/app/app.component';
import { AppStaffComponent } from './staff/app-staff/app-staff.component';
import { CategoriesComponent } from './staff/categories/categories.component';
import { DashboardComponent } from './staff/dashboard/dashboard.component';
import { ProductsComponent } from './staff/products/products.component';
import { ProductsComponent as ProdutsClient } from './client/products/products.component';
import { SingleComponent } from './client/single/single.component';
import { ViewCartComponent } from './client/view-cart/view-cart.component';
import { OrdersCliComponent } from './client/orders-cli/orders-cli.component';
import { OrderDetailsComponent } from './client/order-details/order-details.component';
import { NewCategoryComponent } from './staff/new-category/new-category.component';
import { NewProductComponent } from './staff/new-product/new-product.component';
import { EditProductComponent } from './staff/edit-product/edit-product.component';

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
        {path:'edit/:id', component: EditProductComponent},
        {path:':id', component: SingleComponent},
      ]},
      {path: 'categories' , children: [
        {path: '', component: CategoriesComponent},
        {path: 'new' , component: NewCategoryComponent}
      ]},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
