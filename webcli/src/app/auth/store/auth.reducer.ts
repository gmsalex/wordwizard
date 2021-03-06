import {reducerWithInitialState} from 'typescript-fsa-reducers';
import {LOGIN_ACTION} from './auth.action';
import {Success} from 'typescript-fsa/src/index';
import {AUTH_INITIAL_STATE, AuthState} from './auth.state';
import {UserAuthRequest, UserAuthSuccessResponse} from '../model/definition/user-auth.definition';

export const AUTH_REDUCER = reducerWithInitialState(AUTH_INITIAL_STATE)
  .case(LOGIN_ACTION.done, (state: AuthState, success: Success<UserAuthRequest, UserAuthSuccessResponse>) => ({
    ...state,
    user: {name: (success.result as UserAuthSuccessResponse).name},
    authenticated: true
  }))
  .case(LOGIN_ACTION.failed, (state: AuthState) => ({...state, user: null, authenticated: false}))
  .build();
