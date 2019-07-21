import {AppRootState} from "../../app-store/app-root.state";
import {createFeatureSelector, createSelector} from "@ngrx/store";
import {WWUserDetails} from "../model/definition/user.definition";

export interface AppStateAuthModule extends AppRootState {
  auth: AuthState
}

export interface AuthState {
  readonly user: WWUserDetails;
  readonly authenticated: boolean;
}

export const AUTH_INITIAL_STATE: AuthState = {
  user: null,
  authenticated: false
};

const selectAuthState = createFeatureSelector<AppStateAuthModule, AuthState>('auth');
export const isAuthenticated = createSelector(selectAuthState, v => v.authenticated);
