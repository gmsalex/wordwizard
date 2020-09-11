import {VocabularyEntryDTO} from './vocabulary-entry.definition';
import {VocabularyTagDTO} from './vocabulary-tag.definition';

export interface VECreateDTO {
  readonly entry: VocabularyEntryDTO;
  readonly tagList: VocabularyTagDTO[]
}
