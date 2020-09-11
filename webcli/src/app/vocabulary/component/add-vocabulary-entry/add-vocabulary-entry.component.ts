import {Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {LanguageService} from '../../service/language.service';
import {LanguagePipe} from '../../pipe/language.pipe';
import {buildVeForm, VeForm} from '../../form/create-vocabulary-entry.form';
import {combineLatest, Observable} from 'rxjs';
import {LanguageDTO} from '../../model/language.definition';
import {map} from 'rxjs/operators';
import * as _ from 'lodash';
import {FormBuilder} from '@angular/forms';
import {VocabularyTagDTO} from '../../model/vocabulary-tag.definition';
import {VECreateDTO} from '../../model/vocabulary-entry-create.definition';
import {TagSelectorComponent} from '../tag-selector/tag-selector.component';
import {UsageDatabase} from '../../service/usage/usage-database.service';

@Component({
  selector: 'add-vocabulary-entry',
  templateUrl: './add-vocabulary-entry.component.html',
  styleUrls: ['./add-vocabulary-entry.component.scss'],
  providers: [
    {
      provide: VeForm, useFactory: buildVeForm, deps: [FormBuilder, LanguageService, UsageDatabase]
    },
    UsageDatabase
  ]
})
export class AddVocabularyEntryComponent implements OnInit, OnDestroy {
  languages: Observable<LanguageDTO[]> = this.languageService.listLanguages().pipe();
  @Output() veSave = new EventEmitter<VECreateDTO>();
  @Input() tagsAvailable: Observable<VocabularyTagDTO[]>;
  @ViewChild(TagSelectorComponent) tagSelector;

  constructor(private languageService: LanguageService,
              private languagePipe: LanguagePipe,
              public veForm: VeForm,
              public usageDb: UsageDatabase) {
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

  displayFn() {
    const that = this;
    return (input: string): string => input ? that.languagePipe.transform(that.languageService.getLanguage(input)) : input;
  }

  save() {
    let tags = this.tagSelector.moveInputToSelectedTags();
    this.veForm.setTags(tags);
    this.veSave.emit(this.veForm.buildVocabularyEntry());
    this.veForm.reset();
  }

  onTagSelected(event: VocabularyTagDTO[]) {
    this.veForm.setTags(event);
  }

  ngOnDestroy(): void {
    this.usageDb.close();
  }
}
