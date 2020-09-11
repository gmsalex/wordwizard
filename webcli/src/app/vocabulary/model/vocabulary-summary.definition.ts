import {VocabularyEntryDTO} from './vocabulary-entry.definition';
import {VocabularyTagDTO} from './vocabulary-tag.definition';

export interface VocabularySummaryDTO {
  readonly veList: VocabularyEntryDTO[];
  readonly tagList: VocabularyTagDTO[];
}
