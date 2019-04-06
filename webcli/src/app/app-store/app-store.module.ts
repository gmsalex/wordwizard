import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {StoreModule} from "@ngrx/store";
import {EffectsModule} from "@ngrx/effects";
import {ROOT_INITIAL_STATE} from "./app-root.state";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    StoreModule.forRoot(ROOT_INITIAL_STATE),
    EffectsModule.forRoot([])
  ]
})
export class AppStoreModule {
}
