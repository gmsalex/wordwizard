import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {StoreModule} from '@ngrx/store';
import {EffectsModule} from '@ngrx/effects';
import {AppMaterialModule} from '../app-material/app-material.module';
import {VOCABULARY_REDUCER} from './store/vocabulary.reducer';
import {VocabularyEffects} from './store/vocabulary.effects';
import {ReactiveFormsModule} from '@angular/forms';
import {VocabularyService} from './service/vocabulary.service';
import {RouterModule} from '@angular/router';
import {vocabularyRoutes} from './vocabulary.routes';
import {VocabularyComponent} from './component/vocabulary/vocabulary.component';
import {TextSelectionDirective} from './directive/text-selection.directive';
import {LanguagePipe} from './pipe/language.pipe';
import {LanguageService} from './service/language.service';
import {AddVocabularyEntryComponent} from './component/add-vocabulary-entry/add-vocabulary-entry.component';
import {AddUsageComponent} from './component/add-usage/add-usage.component';
import {ListVocabularyEntryComponent} from './component/list-vocabulary-entry/list-vocabulary-entry.component';
import {TagSelectorComponent} from './component/tag-selector/tag-selector.component';
import {UsageTreeComponent} from './component/usage-tree/usage-tree.component';


@NgModule({
  declarations: [
    VocabularyComponent,
    ListVocabularyEntryComponent,
    AddUsageComponent,
    TextSelectionDirective,
    LanguagePipe,
    AddVocabularyEntryComponent,
    TagSelectorComponent,
    UsageTreeComponent
  ],
  imports: [
    CommonModule,
    AppMaterialModule,
    ReactiveFormsModule,
    StoreModule.forFeature('vocabulary', VOCABULARY_REDUCER),
    EffectsModule.forFeature([VocabularyEffects]),
    RouterModule.forChild(vocabularyRoutes)
  ],
  providers: [
    VocabularyService,
    LanguageService,
    LanguagePipe,
  ]
})
export class VocabularyModule {
}
