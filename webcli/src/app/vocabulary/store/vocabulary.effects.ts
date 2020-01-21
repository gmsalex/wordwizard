import {Injectable} from '@angular/core';
import {Actions, Effect, ofType} from '@ngrx/effects';
import {catchError, exhaustMap, switchMap} from 'rxjs/operators';
import {Action} from 'typescript-fsa';
import {of} from 'rxjs';
import {VocabularyService} from '../service/vocabulary.service';
import {VE_CREATE_ACTION, VS_CREATE_ACTION, VS_LIST_ACTION, VS_VIEW_ACTION} from './vocabulary.action';
import {VECreationWrapper} from '../model/vocabulary-entry.definition';

@Injectable()
export class VocabularyEffects {

  @Effect()
  vsList = this.actions.pipe(
    ofType(VS_LIST_ACTION.started.type),
    exhaustMap((v: Action<void>) => {
      return this.vocabularyService.listVs().pipe(
        switchMap((r) => of(VS_LIST_ACTION.done({params: v.payload, result: r}))),
        catchError(() => of(VS_LIST_ACTION.failed({params: v.payload, error: null})))
      );
    })
  );

  @Effect()
  vsCreate = this.actions.pipe(
    ofType(VS_CREATE_ACTION.started.type),
    exhaustMap((v: Action<VSCreationDTO>) => {
      return this.vocabularyService.createVs(v.payload).pipe(
        switchMap((r) => of(VS_CREATE_ACTION.done({params: v.payload, result: r}))),
        catchError(() => of(VS_CREATE_ACTION.failed({params: v.payload, error: null})))
      );
    })
  );

  @Effect()
  veCreate = this.actions.pipe(
    ofType(VE_CREATE_ACTION.started.type),
    exhaustMap((v: Action<VECreationWrapper>) => {
      return this.vocabularyService.createVe(v.payload).pipe(
        switchMap((r) => of(VE_CREATE_ACTION.done({params: v.payload, result: r}))),
        catchError(() => of(VE_CREATE_ACTION.failed({params: v.payload, error: null})))
      );
    })
  );


  @Effect()
  vsView = this.actions.pipe(
    ofType(VS_VIEW_ACTION.started.type),
    exhaustMap((v: Action<number>) => {
      return this.vocabularyService.listRepetition(v.payload).pipe(
        switchMap((r) => of(VS_VIEW_ACTION.done({params: v.payload, result: r}))),
        catchError(() => of(VS_VIEW_ACTION.failed({params: v.payload, error: null})))
      );
    })
  );

  constructor(private actions: Actions, private vocabularyService: VocabularyService) {
  }
}

