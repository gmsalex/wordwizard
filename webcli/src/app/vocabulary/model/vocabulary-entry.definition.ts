import {MetaData} from './meta-data.definition';

export interface VocabularyEntryDTO {
  readonly id: number;
  readonly term: string;
  readonly language: string;
  readonly created: string;
  readonly meta: MetaData;
}
