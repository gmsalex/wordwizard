import {AppRootState} from "../../app-store/app-root.state";
import {VocabularySelectionView} from "../model/vocabulary-selection-view.definition";

export interface AppStateVocabularyModule extends AppRootState {
  vocabulary: VocabularyState
}

export interface VocabularyState {
  readonly vs: VocabularySelectionDTO[];
  readonly view: VocabularySelectionView
}

export const VOCABULARY_INIT_STATE: VocabularyState = {
  vs: [],
  view: null
};



