import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {createTranslation} from '../model/meta-data.implementation';
import {TranslationEdit} from '../model/meta-data.definition';
import {LanguageService} from '../service/language.service';
import {VocabularyEntryDTO} from '../model/vocabulary-entry.definition';

export class VeForm {
  private readonly translations: TranslationEdit[] = [];

  constructor(private fb: FormBuilder, private languageService: LanguageService) {
  }

  getLanguage() {
    return this.form.controls.language;
  }

  getForm() {
    return this.form;
  }

  addTranslation(value: string) {
    if (value) {
      this.translations.push(createTranslation(value));
    }
  }

  removeTranslation(index: number) {
    this.translations.splice(index, 1);
  }

  mergeTranslationToExamples(index: number) {
    if (index > 0 && index < this.translations.length) {
      const dst: TranslationEdit = this.translations[index - 1];
      const src: TranslationEdit = this.translations[index];
      const example = src.value;
      dst.addExample(example);
      this.removeTranslation(index);
    }
  }

  getTranslations(): TranslationEdit[] {
    return this.translations;
  }

  reset() {
    this.form.reset();
    this.resetTranslations();
  }

  resetTranslations() {
    this.translations.length = 0;
  }

  buildVocabularyEntry(): VocabularyEntryDTO {
    return {...this.form.value as VocabularyEntryDTO, meta: {translations: this.translations}};
  }

  private readonly languageValidator = (control: AbstractControl): ValidationErrors | null =>
    this.languageService.getLanguage(control.value) ? null : {error: 'error'};

  private readonly translationValidator = (control: AbstractControl): ValidationErrors | null =>
    this.translations.length > 0 && this.translations.length < 32 ? null : {error: 'error'};

  private readonly form: FormGroup = this.fb.group({
    term: [null, Validators.compose([Validators.required, Validators.minLength(1), Validators.maxLength(512)])],
    language: [null, Validators.compose([Validators.required, this.languageValidator])],
  }, {
    validators: this.translationValidator
  });
}

export function buildVeForm(fb: FormBuilder, languageService: LanguageService) {
  return new VeForm(fb, languageService);
}
