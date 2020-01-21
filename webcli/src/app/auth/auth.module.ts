import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {StoreModule} from '@ngrx/store';
import {RouterModule} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {AppMaterialModule} from '../app-material/app-material.module';
import {ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {EffectsModule} from '@ngrx/effects';
import {AUTH_REDUCER} from './store/auth.reducer';
import {AuthEffects} from './store/auth.effects';

@NgModule({
  declarations: [LoginComponent],
  imports: [
    CommonModule,
    AppMaterialModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    StoreModule.forFeature('auth', AUTH_REDUCER),
    EffectsModule.forFeature([AuthEffects]),
    RouterModule.forChild([
      {path: '', component: LoginComponent},
    ]),
  ],
})
export class AuthModule {
}
