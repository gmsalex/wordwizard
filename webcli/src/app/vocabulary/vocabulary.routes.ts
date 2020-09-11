import {Routes} from '@angular/router';
import {VocabularyComponent} from './component/vocabulary/vocabulary.component';
import {ListVocabularyEntryComponent} from './component/list-vocabulary-entry/list-vocabulary-entry.component';

export const vocabularyRoutes: Routes = [
    {
        path: '',
        component: VocabularyComponent,
        children: [
            {path: '', pathMatch: 'full', component: ListVocabularyEntryComponent},
        ]
    }
];
