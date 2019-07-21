import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ListVocabularySelectionComponent} from './components/list-vocabulary-selection/list-vocabulary-selection.component';
import {StoreModule} from "@ngrx/store";
import {EffectsModule} from "@ngrx/effects";
import {AppMaterialModule} from "../app-material/app-material.module";
import {VOCABULARY_REDUCER} from "./store/vocabulary.reducer";
import {VocabularyEffects} from "./store/vocabulary.effects";
import {FormBuilder, ReactiveFormsModule} from "@angular/forms";
import {buildVsForm, CREATE_VS_FORM} from "./form/create-vocabulary-selection.form";
import {VocabularyService} from "./service/vocabulary.service";
import {ViewVocabularySelectionComponent} from './components/view-vocabulary-selection/view-vocabulary-selection.component';
import {RouterModule} from "@angular/router";
import {vocabularyRoutes} from "./vocabulary.routes";
import {VocabularyComponent} from './components/vocabulary/vocabulary.component';
import {VocabularySelectionViewGuard} from "./service/vocabulary-selection-view.guard";
import {buildVeForm, VeForm} from "./form/create-vocabulary-entry.form";
import {AddTranslationComponent} from "./components/add-translation/add-translation.component";
import {buildTranslationForm, TERM_TRANSLATION_FORM} from "./form/term-translation.form";

@NgModule({
  declarations: [ListVocabularySelectionComponent, ViewVocabularySelectionComponent, VocabularyComponent, AddTranslationComponent],
  imports: [
    CommonModule,
    AppMaterialModule,
    ReactiveFormsModule,
    StoreModule.forFeature('vocabulary', VOCABULARY_REDUCER),
    EffectsModule.forFeature([VocabularyEffects]),
    RouterModule.forChild(vocabularyRoutes)
  ],
  providers: [
    {
      provide: CREATE_VS_FORM, useFactory: buildVsForm, deps: [FormBuilder]
    },
    {
      provide: VeForm, useFactory: buildVeForm, deps: [FormBuilder]
    },
    {
      provide: TERM_TRANSLATION_FORM, useFactory: buildTranslationForm, deps: [FormBuilder]
    },
    VocabularyService,
    VocabularySelectionViewGuard
  ]
})
export class VocabularyModule {
}
