import actionCreatorFactory, {AsyncActionCreators} from 'typescript-fsa';
import {VocabularySummaryDTO} from '../model/vocabulary-summary.definition';
import {VECreateDTO} from '../model/vocabulary-entry-create.definition';

const actionCreator = actionCreatorFactory();
export const VOCABULARY_SUMMARY_ACTION: AsyncActionCreators<void, VocabularySummaryDTO, any> =
    actionCreator.async<void, VocabularySummaryDTO, any>('VOCABULARY_SUMMARY');
export const VE_CREATE_ACTION: AsyncActionCreators<VECreateDTO, VocabularySummaryDTO, any> =
    actionCreator.async<VECreateDTO, VocabularySummaryDTO, any>('VE_CREATE');


