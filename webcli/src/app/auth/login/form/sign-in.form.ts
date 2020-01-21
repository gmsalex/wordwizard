import {InjectionToken} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

export const SIGN_IN_FORM = new InjectionToken<FormGroup>('Sign in Form');

export function buildSignInForm(fb: FormBuilder) {
  return fb.group({
    email: ['', Validators.compose([Validators.required, Validators.email])],
    password: ['', Validators.compose([Validators.required, Validators.minLength(4), Validators.maxLength(8)])]
  });
}

