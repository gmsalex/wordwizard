import {Injectable} from "@angular/core";
import {Actions, Effect, ofType} from "@ngrx/effects";
import {LOGIN_ACTION} from "./auth.action";
import {catchError, exhaustMap, switchMap, tap} from "rxjs/operators";
import {of} from "rxjs";
import {AuthService} from "../service/auth.service";
import {Action} from "typescript-fsa";
import {Router} from "@angular/router";
import {UserAuthRequest} from "../model/definition/user-auth.definition";

@Injectable()
export class AuthEffects {

  @Effect()
  auth = this.actions.pipe(
    ofType(LOGIN_ACTION.started.type),
    exhaustMap((v: Action<UserAuthRequest>) => {
      return this.authService.login(v.payload).pipe(
        switchMap((r) => of(LOGIN_ACTION.done({params: v.payload, result: r}))),
        catchError(() => of(LOGIN_ACTION.failed({params: v.payload, error: null})))
      );
    })
  );

  @Effect({dispatch: false})
  loginRedirect = this.actions.pipe(
    ofType(LOGIN_ACTION.done.type),
    tap(() => this.router.navigate(['dashboard']))
  );

  constructor(private actions: Actions, private authService: AuthService, private router: Router) {
  }
}

