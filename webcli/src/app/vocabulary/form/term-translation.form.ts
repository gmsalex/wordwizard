import {InjectionToken} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";


export const TERM_TRANSLATION_FORM = new InjectionToken<FormGroup>('Term translation form');

export function buildTranslationForm(fb: FormBuilder) {
  return fb.group({
    translation: [null, Validators.compose([Validators.required, Validators.minLength(1)])],
  });
}

