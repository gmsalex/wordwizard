import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Store} from "@ngrx/store";
import {AppStateVocabularyModule} from "../../store/vocabulary.state";
import {VS_VIEW_ACTION} from "../../store/vocabulary.action";
import * as vocabularySelector from "../../store/vocabulary.selector";
import {Observable} from "rxjs";
import {VocabularySelectionView} from "../../model/vocabulary-selection-view.definition";
import {VeForm} from "../../form/create-vocabulary-entry.form";

@Component({
  selector: 'view-vocabulary-selection',
  templateUrl: './view-vocabulary-selection.component.html',
  styleUrls: ['./view-vocabulary-selection.component.scss']
})
export class ViewVocabularySelectionComponent implements OnInit {
  vsView$: Observable<VocabularySelectionView> = this.store.select(vocabularySelector.vsView);

  constructor(private route: ActivatedRoute,
              private store: Store<AppStateVocabularyModule>,
              private veForm: VeForm) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(routeParam => {
      this.store.dispatch(VS_VIEW_ACTION.started(Number.parseInt(routeParam.get('id'))));
      this.veForm.reset();
    });
  }
}
