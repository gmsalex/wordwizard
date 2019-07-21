import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {select, Store} from "@ngrx/store";
import {map, take} from "rxjs/operators";
import * as fromVocabulary from "../store/vocabulary.state";
import * as vocabularySelector from "../store/vocabulary.selector";
import * as _ from "lodash";
import {Injectable} from "@angular/core";

@Injectable()
export class VocabularySelectionViewGuard implements CanActivate {
  constructor(private store: Store<fromVocabulary.AppStateVocabularyModule>) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const idValue = Number.parseInt(route.paramMap.get('id'));
    return <Observable<boolean>>this.store.pipe(
      select(vocabularySelector.vsList),
      map(vsList => _.find(vsList, {id: idValue}) != null),
      take(1),
    );
  }
}

