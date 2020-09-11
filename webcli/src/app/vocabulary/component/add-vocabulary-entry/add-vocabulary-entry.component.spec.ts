import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AddVocabularyEntryComponent} from './add-vocabulary-entry.component';

describe('AddVocabularyEntryComponent', () => {
  let component: AddVocabularyEntryComponent;
  let fixture: ComponentFixture<AddVocabularyEntryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddVocabularyEntryComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddVocabularyEntryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
