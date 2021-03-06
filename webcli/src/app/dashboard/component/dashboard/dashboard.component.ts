import {Component, OnInit} from '@angular/core';
import {VOCABULARY_SUMMARY_ACTION} from '../../../vocabulary/store/vocabulary.action';
import {Store} from '@ngrx/store';
import * as fromVocabulary from '../../../vocabulary/store/vocabulary.state';

@Component({
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  constructor(private store: Store<fromVocabulary.AppStateVocabularyModule>) {
  }

  ngOnInit() {
    this.store.dispatch(VOCABULARY_SUMMARY_ACTION.started(null));
  }
}
