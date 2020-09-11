import {createFeatureSelector, createSelector} from '@ngrx/store';
import {AppStateVocabularyModule, VocabularyState} from './vocabulary.state';

const selectVocabularyState = createFeatureSelector<AppStateVocabularyModule, VocabularyState>('vocabulary');
export const veList = createSelector(selectVocabularyState, v => v.veList);
export const tagList = createSelector(selectVocabularyState, v => v.tagList);

