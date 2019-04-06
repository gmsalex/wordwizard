import {WWUserPrincipal} from "./definition/user.definition";
import {AppRootState} from "../../app-store/app-root.state";
import {createFeatureSelector, createSelector} from "@ngrx/store";

export interface State extends AppRootState {
  user: AuthState
}

export interface AuthState {
  readonly user: WWUserPrincipal;
  readonly authenticated: boolean;
}

export const AUTH_INITIAL_STATE: AuthState = {
  user: null,
  authenticated: false
};

const selectAuthState = createFeatureSelector<State, AuthState>('user');
export const isAuthenticated = createSelector(selectAuthState, v => v.authenticated);

//
// export const selectAuthStatusState = createSelector(
//   selectAuthState,
//   (state: AuthState) => state.status
// );
// export const getUser = createSelector(selectAuthStatusState, fromAuth.getUser);
// export const getLoggedIn = createSelector(getUser, user => !!user);
//
//
// const authFeature = ()
