import {AppRootState} from '../../app-store/app-root.state';
import {VocabularyEntryDTO} from '../model/vocabulary-entry.definition';
import {VocabularyTagDTO} from '../model/vocabulary-tag.definition';

export interface AppStateVocabularyModule extends AppRootState {
  vocabulary: VocabularyState;
}

export interface VocabularyState {
  readonly veList: VocabularyEntryDTO[];
  readonly tagList: VocabularyTagDTO[];
}

export const VOCABULARY_INIT_STATE: VocabularyState = {
  veList: [],
  tagList: []
};



