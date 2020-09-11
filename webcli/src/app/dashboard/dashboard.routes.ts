import {Routes} from '@angular/router';
import {DashboardComponent} from './component/dashboard/dashboard.component';

export const dashboardRoutes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    children: [
      {path: '', redirectTo: 'vs', pathMatch: 'full'},
      {path: 'vs', loadChildren: '../vocabulary/vocabulary.module#VocabularyModule'},
    ]
  }
];
