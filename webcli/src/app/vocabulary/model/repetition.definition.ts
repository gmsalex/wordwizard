import {VocabularyEntryDTO} from './vocabulary-entry.definition';

export interface RepetitionDTO {
  readonly id: number;
  readonly entry: VocabularyEntryDTO;
}
