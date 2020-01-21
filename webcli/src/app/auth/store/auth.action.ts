import actionCreatorFactory, {AsyncActionCreators} from 'typescript-fsa';
import {UserAuthRequest, UserAuthSuccessResponse} from '../model/definition/user-auth.definition';

const actionCreator = actionCreatorFactory();
export const LOGIN_ACTION: AsyncActionCreators<UserAuthRequest, UserAuthSuccessResponse, any> =
  actionCreator.async<UserAuthRequest, UserAuthSuccessResponse, any>('LOGIN');
