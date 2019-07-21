import {MetaData} from "./meta-data.definition";

export interface VocabularyEntryDTO {
  readonly id: number;
  readonly term: number;
  readonly language: string;
  readonly meta: MetaData;
}

