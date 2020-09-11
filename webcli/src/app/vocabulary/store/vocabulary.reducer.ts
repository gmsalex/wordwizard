import {reducerWithInitialState} from 'typescript-fsa-reducers';
import {VOCABULARY_INIT_STATE, VocabularyState} from './vocabulary.state';
import {VE_CREATE_ACTION, VOCABULARY_SUMMARY_ACTION} from './vocabulary.action';

export const VOCABULARY_REDUCER = reducerWithInitialState(VOCABULARY_INIT_STATE)
    .case(VOCABULARY_SUMMARY_ACTION.done, (state: VocabularyState, success) => ({
        ...success.result
    }))
  .case(VE_CREATE_ACTION.done, (state: VocabularyState, success) => ({
      ...success.result
  }))
  .build();
