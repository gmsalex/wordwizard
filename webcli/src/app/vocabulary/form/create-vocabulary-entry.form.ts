import {AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

export function buildVeForm(fb: FormBuilder) {
  return new VeForm(fb);
}

export class VeForm {
  private readonly _form: FormGroup = this.fb.group({
    term: [null, Validators.compose([Validators.required, Validators.minLength(1), Validators.maxLength(512)])],
    language: [null],
    meta: this.fb.group({
      translations: this.fb.array([])
    })
  });
  private readonly _translations: VeTranslation[] = [];

  constructor(private fb: FormBuilder) {
  }

  get form() {
    return this._form;
  }

  addTranslation(value: string) {
    const group = this.initTranslationFormGroup(value);
    this._translations.push(new VeTranslation(group));
    (<FormArray>this._form.get('meta.translations'))
      .push(group);
  }

  removeTranslation(index: number) {
    (<FormArray>this._form.get('meta.translations')).removeAt(index);
    this._translations.splice(index, 1);
  }

  mergeTranslationToExamples(index: number) {
    if (index > 0) {
      const translations = (<FormArray>this._form.get('meta.translations'));
      const dst: FormGroup = <FormGroup>translations.at(index - 1);
      const example = (<FormGroup>translations.at(index)).controls.translation.value;
      this.removeTranslation(index);
      (<FormArray>dst.controls.examples).push(new FormControl(example));
    }
  }

  translations(): VeTranslation[] {
    return this._translations;
  }

  reset() {
    this._form.reset();
  }

  private initTranslationFormGroup(value: string): FormGroup {
    return this.fb.group({
      translation: [value],
      examples: this.fb.array([])
    })
  }
}

export class VeTranslation {
  _translation: AbstractControl = this._form.get('translation');

  constructor(private _form: FormGroup) {
  }

  _examples: FormArray = <FormArray>this._form.get('examples');

  get examples(): AbstractControl[] {
    return this._examples.controls;
  }

  get value() {
    return this._translation.value;
  }

  removeExample(index: number) {
    this._examples.removeAt(index);
  }
}
