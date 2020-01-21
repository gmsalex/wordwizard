import {Component, OnDestroy, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {Store} from '@ngrx/store';
import {AppStateVocabularyModule} from '../../store/vocabulary.state';
import {VS_VIEW_ACTION} from '../../store/vocabulary.action';
import * as vocabularySelector from '../../store/vocabulary.selector';
import {BehaviorSubject, Observable, Subscription} from 'rxjs';
import {VocabularySelectionView} from '../../model/vocabulary-selection-view.definition';
import {LanguagePipe} from '../../pipe/language.pipe';

@Component({
  selector: 'view-vocabulary-selection',
  templateUrl: './view-vocabulary-selection.component.html',
  styleUrls: ['./view-vocabulary-selection.component.scss'],
  providers: [
    LanguagePipe
  ]
})
export class ViewVocabularySelectionComponent implements OnInit, OnDestroy {
  vsView: Observable<VocabularySelectionView> = this.store.select(vocabularySelector.vsView);
  subscription: Subscription;
  vsId: BehaviorSubject<number> = new BehaviorSubject<number>(null);

  constructor(private route: ActivatedRoute,
              private store: Store<AppStateVocabularyModule>,
              private location: Location) {
  }

  ngOnInit() {
    this.subscription = this.route.paramMap.subscribe(routeParam => {
      const vsIdRouteParam = Number.parseInt(routeParam.get('id'), 10);
      this.store.dispatch(VS_VIEW_ACTION.started(vsIdRouteParam));
      this.vsId.next(vsIdRouteParam);
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  clickBack() {
    this.location.back();
  }
}
