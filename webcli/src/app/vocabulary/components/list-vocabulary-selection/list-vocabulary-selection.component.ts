import {Component, Inject, OnInit} from '@angular/core';
import {select, Store} from "@ngrx/store";
import * as fromVocabulary from "../../store/vocabulary.state";
import * as vocabularySelector from "../../store/vocabulary.selector";
import {Observable} from "rxjs";
import {FormGroup} from "@angular/forms";
import {CREATE_VS_FORM} from "../../form/create-vocabulary-selection.form";
import {VS_CREATE_ACTION} from "../../store/vocabulary.action";

@Component({
  selector: 'list-vocabulary-selection',
  templateUrl: './list-vocabulary-selection.component.html',
  styleUrls: ['./list-vocabulary-selection.component.scss']
})
export class ListVocabularySelectionComponent implements OnInit {
  vsList: Observable<VocabularySelectionDTO[]> = this.store.pipe(select(vocabularySelector.vsList));

  constructor(private store: Store<fromVocabulary.AppStateVocabularyModule>, @Inject(CREATE_VS_FORM) public createVsForm: FormGroup) {
  }

  ngOnInit() {
  }

  create() {
    this.store.dispatch(VS_CREATE_ACTION.started(this.createVsForm.value));
    this.createVsForm.reset();
  }

  view(id) {

  }
}
