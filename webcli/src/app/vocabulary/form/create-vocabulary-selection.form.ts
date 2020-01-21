import {InjectionToken} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import * as XRegExp from 'xregexp';


export const CREATE_VS_FORM = new InjectionToken<FormGroup>('Create vocabulary selection form');

export function buildVsForm(fb: FormBuilder) {
  return fb.group({
    name: [null, Validators.compose([Validators.required, Validators.pattern(XRegExp('^(\\p{L}|\\d|\\s){1,64}$', 'A'))])],
  });
}

