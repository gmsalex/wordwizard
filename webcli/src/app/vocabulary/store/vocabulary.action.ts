import actionCreatorFactory, {AsyncActionCreators} from 'typescript-fsa';
import {RepetitionDTO} from '../model/repetition.definition';
import {VECreationWrapper} from '../model/vocabulary-entry.definition';

const actionCreator = actionCreatorFactory();
export const VS_LIST_ACTION: AsyncActionCreators<void, VocabularySelectionDTO[], any> =
  actionCreator.async<void, VocabularySelectionDTO[], any>('VS_LIST');
export const VS_CREATE_ACTION: AsyncActionCreators<VSCreationDTO, VocabularySelectionDTO, any> =
  actionCreator.async<VSCreationDTO, VocabularySelectionDTO, any>('VS_CREATE');
export const VS_VIEW_ACTION: AsyncActionCreators<number, RepetitionDTO[], any> =
  actionCreator.async<number, RepetitionDTO[], any>('VS_VIEW');
export const VE_CREATE_ACTION: AsyncActionCreators<VECreationWrapper, RepetitionDTO, any> =
  actionCreator.async<VECreationWrapper, RepetitionDTO, any>('VE_CREATE');


