import {Component, OnInit} from '@angular/core';
import {select, Store} from '@ngrx/store';
import * as fromVocabulary from '../../store/vocabulary.state';
import * as vocabularySelector from '../../store/vocabulary.selector';
import {Observable} from 'rxjs';
import {VE_CREATE_ACTION} from '../../store/vocabulary.action';
import {VocabularyEntryDTO} from '../../model/vocabulary-entry.definition';
import {VocabularyTagDTO} from '../../model/vocabulary-tag.definition';
import {VECreateDTO} from '../../model/vocabulary-entry-create.definition';

@Component({
  selector: 'list-vocabulary-entry',
  templateUrl: './list-vocabulary-entry.component.html',
  styleUrls: ['./list-vocabulary-entry.component.scss'],
})
export class ListVocabularyEntryComponent implements OnInit {
  veList: Observable<VocabularyEntryDTO[]> = this.store.pipe(select(vocabularySelector.veList));
  tagList: Observable<VocabularyTagDTO[]> = this.store.pipe(select(vocabularySelector.tagList));

  constructor(private store: Store<fromVocabulary.AppStateVocabularyModule>) {
  }

  ngOnInit() {
  }

  onVeSave(ve: VECreateDTO) {
    this.store.dispatch(VE_CREATE_ACTION.started(ve));
  }
}
