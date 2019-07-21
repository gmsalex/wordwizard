import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AuthModule} from "./auth/auth.module";
import {AppStoreModule} from "./app-store/app-store.module";
import {StoreDevtoolsModule} from "@ngrx/store-devtools";
import {AppMaterialModule} from "./app-material/app-material.module";
import {BASE_URL, BaseUrlInterceptor} from "./interceptor/base-url.interceptor";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ApplyCredentialsInterceptor} from "./interceptor/apply-credentials.interceptor";
import {environment} from "../environments/environment";
import {ProgressInterceptor} from "./interceptor/progress.interceptor";
import {StoreRouterConnectingModule} from "@ngrx/router-store";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    StoreDevtoolsModule.instrument({}),
    HttpClientModule,
    AppMaterialModule,
    AppRoutingModule,
    AppStoreModule,
    AuthModule,
    StoreRouterConnectingModule.forRoot(),
  ],
  providers: [
    {
      provide: BASE_URL,
      useValue: environment.baseUrl
    },
    {
      provide: HTTP_INTERCEPTORS,
      multi: true,
      useClass: BaseUrlInterceptor
    },
    {
      provide: HTTP_INTERCEPTORS,
      multi: true,
      useClass: ApplyCredentialsInterceptor
    },
    {
      provide: HTTP_INTERCEPTORS,
      multi: true,
      useClass: ProgressInterceptor
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
