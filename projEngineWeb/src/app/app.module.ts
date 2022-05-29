import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgxEchartsModule } from 'ngx-echarts';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './components/menu/menu.component';
import { StoresComponent } from './pages/stores/stores.component';
import { RidersComponent } from './pages/riders/riders.component';
import { InfoBoxComponent } from './components/info-box/info-box.component';
import { PieChartComponent } from './components/pie-chart/pie-chart.component';
import { LineChartComponent } from './components/line-chart/line-chart.component';
import { TableComponent } from './components/table/table.component';
import { RiderComponent } from './pages/rider/rider.component';
import { StoreComponent } from './pages/store/store.component';
import { OrdersComponent } from './pages/orders/orders.component';
import { OrderComponent } from './pages/order/order.component';
import { LoginComponent } from './pages/login/login.component';
import { BarChartComponent } from './components/bar-chart/bar-chart.component';
import { RegisterComponent } from './pages/register/register.component';
import { NavbarComponent } from './components/navbar/navbar.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    StoresComponent,
    RidersComponent,
    InfoBoxComponent,
    PieChartComponent,
    LineChartComponent,
    TableComponent,
    RiderComponent,
    StoreComponent,
    OrdersComponent,
    OrderComponent,
    LoginComponent,
    BarChartComponent,
    RegisterComponent,
    NavbarComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts')
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }