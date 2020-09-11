import {InjectionToken} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';


export const TERM_USAGE_FORM = new InjectionToken<FormGroup>('Term usage form');

export function buildUsageForm(fb: FormBuilder) {
  return fb.group({
    usage: [null, Validators.compose([Validators.required, Validators.minLength(1)])],
  });
}

