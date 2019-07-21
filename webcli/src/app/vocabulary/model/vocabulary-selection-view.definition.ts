import {RepetitionDTO} from "./repetition.definition";

export interface VocabularySelectionView {
  readonly vs: VocabularySelectionDTO;
  readonly repetition: RepetitionDTO[];
}
