import {createFeatureSelector, createSelector} from "@ngrx/store";
import {AppStateVocabularyModule, VocabularyState} from "./vocabulary.state";

const selectVocabularyState = createFeatureSelector<AppStateVocabularyModule, VocabularyState>('vocabulary');
export const vsList = createSelector(selectVocabularyState, v => v.vs);
export const vsView = createSelector(selectVocabularyState, v => v.view);
