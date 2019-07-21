import {Routes} from "@angular/router";
import {VocabularyComponent} from "./components/vocabulary/vocabulary.component";
import {ListVocabularySelectionComponent} from "./components/list-vocabulary-selection/list-vocabulary-selection.component";
import {ViewVocabularySelectionComponent} from "./components/view-vocabulary-selection/view-vocabulary-selection.component";
import {VocabularySelectionViewGuard} from "./service/vocabulary-selection-view.guard";

export const vocabularyRoutes: Routes = [
  {
    path: '',
    component: VocabularyComponent,
    children: [
      {path: '', pathMatch: 'full', component: ListVocabularySelectionComponent},
      {path: ':id', component: ViewVocabularySelectionComponent, canActivate: [VocabularySelectionViewGuard]},
    ]
  }
];
