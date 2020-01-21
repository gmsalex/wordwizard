import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ListVocabularySelectionComponent} from './components/list-vocabulary-selection/list-vocabulary-selection.component';
import {StoreModule} from '@ngrx/store';
import {EffectsModule} from '@ngrx/effects';
import {AppMaterialModule} from '../app-material/app-material.module';
import {VOCABULARY_REDUCER} from './store/vocabulary.reducer';
import {VocabularyEffects} from './store/vocabulary.effects';
import {ReactiveFormsModule} from '@angular/forms';
import {VocabularyService} from './service/vocabulary.service';
import {ViewVocabularySelectionComponent} from './components/view-vocabulary-selection/view-vocabulary-selection.component';
import {RouterModule} from '@angular/router';
import {vocabularyRoutes} from './vocabulary.routes';
import {VocabularyComponent} from './components/vocabulary/vocabulary.component';
import {VocabularySelectionViewGuard} from './service/vocabulary-selection-view.guard';
import {AddTranslationComponent} from './components/add-translation/add-translation.component';
import {TextSelectionDirective} from './directive/text-selection.directive';
import {LanguagePipe} from './pipe/language.pipe';
import {LanguageService} from './service/language.service';
import {AddVocabularyEntryComponent} from './components/add-vocabulary-entry/add-vocabulary-entry.component';

@NgModule({
  declarations: [
    ListVocabularySelectionComponent,
    ViewVocabularySelectionComponent,
    VocabularyComponent,
    AddTranslationComponent,
    TextSelectionDirective,
    LanguagePipe,
    AddVocabularyEntryComponent
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
    VocabularySelectionViewGuard
  ]
})
export class VocabularyModule {
}
