import {VocabularyEntryDTO} from './vocabulary-entry.definition';

export interface RepetitionDTO {
  readonly id: number;
  readonly created: string;
  readonly entry: VocabularyEntryDTO;
}
