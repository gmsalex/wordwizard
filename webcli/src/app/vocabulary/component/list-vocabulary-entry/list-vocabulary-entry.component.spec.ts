import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ListVocabularyEntryComponent} from './list-vocabulary-entry.component';

describe('ListVocabularyEntryComponent', () => {
  let component: ListVocabularyEntryComponent;
  let fixture: ComponentFixture<ListVocabularyEntryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ListVocabularyEntryComponent]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListVocabularyEntryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
