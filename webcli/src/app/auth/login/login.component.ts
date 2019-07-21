import {Component, Inject} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {buildSignInForm, SIGN_IN_FORM} from "./form/sign-in.form";
import {Store} from "@ngrx/store";
import * as fromAuth from "../store/auth.state";
import {LOGIN_ACTION} from "../store/auth.action";

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [
    {
      provide: SIGN_IN_FORM, useFactory: buildSignInForm, deps: [FormBuilder]
    }
  ]
})
export class LoginComponent {
  constructor(@Inject(SIGN_IN_FORM) public authForm: FormGroup, private store: Store<fromAuth.AppStateAuthModule>) {
  }

  onSubmit() {
    this.store.dispatch(LOGIN_ACTION.started(this.authForm.value));
  }
}

