import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  Validators
} from '@angular/forms';

import {LanguageService} from '../service/language.service';
import {VocabularyTagDTO} from '../model/vocabulary-tag.definition';
import {VECreateDTO} from '../model/vocabulary-entry-create.definition';
import {VocabularyEntryDTO} from '../model/vocabulary-entry.definition';
import {BehaviorSubject} from 'rxjs';
import {UsageEdit} from '../model/usage/usage.definition';
import {UsageDatabase} from '../service/usage/usage-database.service';

export class VeForm {
  private readonly usages: UsageEdit[] = [];
  private resetSubject: BehaviorSubject<void> = new BehaviorSubject(null);

  constructor(private fb: FormBuilder, private languageService: LanguageService, private usageDb: UsageDatabase) {
    this.usageDb.data.subscribe(usages =>
        this.usageWrapper(() => {
          this.usages.length = 0;
          usages.forEach(v => this.usages.push(v));
        }));
  }

  reset() {
    this.usageWrapper(() => {
      this.form.reset({tagsList: this.form.controls.tagsList.value, language: this.form.controls.language.value});
      this.resetSubject.next(null);
    });
  }

  setTags(tags: VocabularyTagDTO[]) {
    let tagsControl: FormArray = this.form.controls.tagsList as FormArray;
    while (tagsControl.length > 0) {
      tagsControl.removeAt(0);
    }
    tags.forEach(v => tagsControl.push(new FormControl(v)));
  }

  getResetSubject() {
    return this.resetSubject;
  }

  buildVocabularyEntry(): VECreateDTO {
    return {
      entry: {
        term: this.form.controls.term.value,
        language: this.form.controls.language.value,
        meta: {
          usages: this.usages
        }
      } as unknown as VocabularyEntryDTO,
      tagList: [...this.form.controls.tagsList.value]
    };
  }

  getLanguage() {
    return this.form.controls.language;
  }

  getForm() {
    return this.form;
  }

  private readonly languageValidator = (control: AbstractControl): ValidationErrors | null =>
      !control.value || this.languageService.getLanguage(control.value) ? null : {error: 'error'};

  private readonly usageValidator = (control: AbstractControl): ValidationErrors | null =>
      this.usages.length > 0 && this.usages.length < 128 ? null : {error: 'error'};

  private readonly form: FormGroup = this.fb.group({
    term: [null, Validators.compose([Validators.required, Validators.minLength(1), Validators.maxLength(512)])],
    language: [null, Validators.compose([this.languageValidator])],
    tagsList: new FormArray([]),
  }, {
    validators: this.usageValidator
  });

  private readonly usageWrapper = (wrappedFunc: () => any) => {
    wrappedFunc();
    console.log('call validity');
    this.form.updateValueAndValidity();
  };
}

export function buildVeForm(fb: FormBuilder, languageService: LanguageService, usageDb: UsageDatabase) {
  return new VeForm(fb, languageService, usageDb);
}
