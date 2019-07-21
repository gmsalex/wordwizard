import {reducerWithInitialState} from "typescript-fsa-reducers";
import {VOCABULARY_INIT_STATE, VocabularyState} from "./vocabulary.state";
import {VS_CREATE_ACTION, VS_LIST_ACTION, VS_VIEW_ACTION} from "./vocabulary.action";
import * as _ from "lodash";

export const VOCABULARY_REDUCER = reducerWithInitialState(VOCABULARY_INIT_STATE)
  .case(VS_LIST_ACTION.done, (state: VocabularyState, success) => ({
    ...state, vs: success.result
  }))
  .case(VS_CREATE_ACTION.done, (state: VocabularyState, success) => ({
    ...state, vs: [...state.vs, success.result]
  }))
  .case(VS_VIEW_ACTION.started, (state: VocabularyState, id) => ({
    ...state, view: {vs: _.find(state.vs, {id: id}), repetition: []}
  }))
  .case(VS_VIEW_ACTION.done, (state: VocabularyState, success) => ({
    ...state, view: {...state.view, repetition: success.result}
  }))
  .build();
