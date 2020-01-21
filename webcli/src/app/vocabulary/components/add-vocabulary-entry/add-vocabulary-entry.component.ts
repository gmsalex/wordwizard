import {Component, Input, OnInit} from '@angular/core';
import {LanguageService} from '../../service/language.service';
import {LanguagePipe} from '../../pipe/language.pipe';
import {buildVeForm, VeForm} from '../../form/create-vocabulary-entry.form';
import {BehaviorSubject, combineLatest, Observable} from 'rxjs';
import {LanguageDTO} from '../../model/language.definition';
import {map} from 'rxjs/operators';
import * as _ from 'lodash';
import {FormBuilder} from '@angular/forms';
import {Store} from '@ngrx/store';
import {AppStateVocabularyModule} from '../../store/vocabulary.state';
import {VE_CREATE_ACTION} from '../../store/vocabulary.action';
import {VECreationWrapper} from '../../model/vocabulary-entry.definition';

@Component({
  selector: 'add-vocabulary-entry',
  templateUrl: './add-vocabulary-entry.component.html',
  styleUrls: ['./add-vocabulary-entry.component.scss'],
  providers: [
    {
      provide: VeForm, useFactory: buildVeForm, deps: [FormBuilder, LanguageService]
    }
  ]
})
export class AddVocabularyEntryComponent implements OnInit {
  languages: Observable<LanguageDTO[]> = this.languageService.listLanguages().pipe();
  selectedText: string;
  @Input()
  private vsId: BehaviorSubject<number>;

  constructor(private languageService: LanguageService,
              private languagePipe: LanguagePipe,
              public veForm: VeForm,
              private store: Store<AppStateVocabularyModule>) {
  }

  ngOnInit() {
    this.languages = combineLatest(
      this.veForm.getLanguage().valueChanges,
      this.languageService.listLanguages()
    ).pipe(
      map((v: any[]) => {
        const input = v[0] || '';
        const languages = v[1] as LanguageDTO[];
        return _.filter(languages, (value: LanguageDTO) => value.nativeName.toLowerCase().indexOf(input.toLowerCase()) >= 0 || value.name.toLowerCase().indexOf(input.toLowerCase()) >= 0);
      })
    );
  }

  onTextSelection(text) {
    this.selectedText = text;
  }

  displayFn() {
    const that = this;
    return (input: string): string => input ? that.languagePipe.transform(that.languageService.getLanguage(input)) : input;
  }

  save() {
    const payload = {ve: this.veForm.buildVocabularyEntry(), vsId: this.vsId.value} as VECreationWrapper;
    this.store.dispatch(VE_CREATE_ACTION.started(payload));
  }
}
