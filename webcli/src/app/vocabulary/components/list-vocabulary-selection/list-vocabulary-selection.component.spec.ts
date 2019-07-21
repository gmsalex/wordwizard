import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ListVocabularySelectionComponent} from './list-vocabulary-selection.component';

describe('ListVocabularySelectionComponent', () => {
  let component: ListVocabularySelectionComponent;
  let fixture: ComponentFixture<ListVocabularySelectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ListVocabularySelectionComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListVocabularySelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
