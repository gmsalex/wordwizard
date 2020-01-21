import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {AppMaterialModule} from '../app-material/app-material.module';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {dashboardRoutes} from './dashboard.routes';

@NgModule({
  declarations: [DashboardComponent],
  imports: [
    CommonModule,
    AppMaterialModule,
    RouterModule.forChild(dashboardRoutes),
  ],
})
export class DashboardModule {
}
