import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AppComponent as AppClient } from './client/app/app.component';
import { FooterComponent } from './client/footer/footer.component';
import { ProductsComponent as ProductsClient } from './client/products/products.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { AppStaffComponent } from './staff/app-staff/app-staff.component';
import { DashboardComponent } from './staff/dashboard/dashboard.component';
import { ProductsComponent } from './staff/products/products.component';
import { CategoriesComponent } from './staff/categories/categories.component';
import { SingleComponent } from './client/single/single.component';
import { ViewCartComponent } from './client/view-cart/view-cart.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OrdersCliComponent } from './client/orders-cli/orders-cli.component';
import { OrderDetailsComponent } from './client/order-details/order-details.component';
import { NewCategoryComponent } from './staff/new-category/new-category.component';
import { NewProductComponent } from './staff/new-product/new-product.component';
import { ProductComponent } from './components/client/product/product.component';
import { AddProductComponent } from './components/client/addproduct/addproduct.component';
import { ShowProductComponent } from './staff/show-product/show-product.component';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    AppClient,
    NavBarComponent,
    AppStaffComponent,
    DashboardComponent,
    ProductsComponent,
    CategoriesComponent,
    ProductsClient,
    SingleComponent,
    ViewCartComponent,
    OrdersCliComponent,
    OrderDetailsComponent,
    NewCategoryComponent,
    NewProductComponent,
    ProductComponent,
    AddProductComponent,
    ShowProductComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  //providers: [ { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true } ],
  bootstrap: [AppComponent]
})
export class AppModule { }
