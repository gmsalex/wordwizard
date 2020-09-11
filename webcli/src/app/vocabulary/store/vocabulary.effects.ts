import {Injectable} from '@angular/core';
import {Actions, Effect, ofType} from '@ngrx/effects';
import {catchError, exhaustMap, switchMap} from 'rxjs/operators';
import {Action} from 'typescript-fsa';
import {of} from 'rxjs';
import {VocabularyService} from '../service/vocabulary.service';
import {VE_CREATE_ACTION, VOCABULARY_SUMMARY_ACTION} from './vocabulary.action';
import {VECreateDTO} from '../model/vocabulary-entry-create.definition';

@Injectable()
export class VocabularyEffects {

  @Effect()
  vsList = this.actions.pipe(
    ofType(VOCABULARY_SUMMARY_ACTION.started.type),
    exhaustMap((v: Action<void>) => {
      return this.vocabularyService.getVocabularySummary().pipe(
        switchMap((r) => of(VOCABULARY_SUMMARY_ACTION.done({params: v.payload, result: r}))),
        catchError(() => of(VOCABULARY_SUMMARY_ACTION.failed({params: v.payload, error: null})))
      );
    })
  );

  @Effect()
  veCreate = this.actions.pipe(
    ofType(VE_CREATE_ACTION.started.type),
    exhaustMap((v: Action<VECreateDTO>) => {
      return this.vocabularyService.createVe(v.payload).pipe(
        switchMap((r) => of(VE_CREATE_ACTION.done({params: v.payload, result: r}))),
        catchError(() => of(VE_CREATE_ACTION.failed({params: v.payload, error: null})))
      );
    })
  );

  constructor(private actions: Actions, private vocabularyService: VocabularyService) {
  }
}

