import {reducerWithInitialState} from "typescript-fsa-reducers";
import {LOGIN_ACTION} from "./auth.action";
import {Success} from "typescript-fsa/src/index";
import {UserAuthRequest, UserAuthSuccessResponse} from "./definition/user-auth.definition";
import {AUTH_INITIAL_STATE, AuthState} from "./auth.state";

export const AUTH_REDUCER = reducerWithInitialState(AUTH_INITIAL_STATE)
  .case(LOGIN_ACTION.done, (state: AuthState, success: Success<UserAuthRequest, UserAuthSuccessResponse>) => ({
    ...state,
    user: {name: (<UserAuthSuccessResponse>success.result).name},
    authenticated: true
  }))
  .case(LOGIN_ACTION.failed, (state: AuthState) => ({...state, user: null, authenticated: false}))
  .build();
