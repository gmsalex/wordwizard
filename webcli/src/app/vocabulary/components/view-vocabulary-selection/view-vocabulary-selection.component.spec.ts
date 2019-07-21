import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewVocabularySelectionComponent} from './view-vocabulary-selection.component';

describe('ViewVocabularySelectionComponent', () => {
  let component: ViewVocabularySelectionComponent;
  let fixture: ComponentFixture<ViewVocabularySelectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ViewVocabularySelectionComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewVocabularySelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
