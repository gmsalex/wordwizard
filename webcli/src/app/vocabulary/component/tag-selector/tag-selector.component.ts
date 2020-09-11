import {Component, ElementRef, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {FormControl} from '@angular/forms';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatAutocompleteSelectedEvent} from '@angular/material';
import * as _ from 'lodash';
import {map, startWith, takeUntil} from 'rxjs/operators';
import {BehaviorSubject, combineLatest, Observable} from 'rxjs';
import {VocabularyTagDTO} from '../../model/vocabulary-tag.definition';

@Component({
  selector: 'tag-selector',
  templateUrl: './tag-selector.component.html',
  styleUrls: ['./tag-selector.component.scss'],
})
export class TagSelectorComponent implements OnInit, OnDestroy {
  control: FormControl = new FormControl();
  separatorKeysCodes: number[] = [ENTER, COMMA];
  selectedTags: BehaviorSubject<VocabularyTagDTO[]> = new BehaviorSubject([]);
  filteredTags: Observable<VocabularyTagDTO[]>;
  unsubscribe: BehaviorSubject<void> = new BehaviorSubject(null);
  @Input() tagsAvailable: Observable<VocabularyTagDTO[]>;
  @Output() tagSelectorChange: EventEmitter<VocabularyTagDTO[]> = new EventEmitter();
  @ViewChild('tagInput') tagInput: ElementRef<HTMLInputElement>;
  @Input() resetNotification: Observable<void>;

  constructor() {
  }

  ngOnInit() {
    this.filteredTags = combineLatest(this.control.valueChanges.pipe(startWith(null)), this.tagsAvailable)
        .pipe(
            map((values: any[]) => {
              let [input, available] = values;
              input = ((input || '') + '').toLowerCase();
              return _.filter(available as VocabularyTagDTO[], v => v.name.toLowerCase().indexOf(input) >= 0 && this.isTagNotSelected(input));
            })
        );
    this.selectedTags.pipe(takeUntil(this.unsubscribe)).subscribe(v => this.tagSelectorChange.emit(v));
    this.resetNotification.pipe(takeUntil(this.unsubscribe)).subscribe(v => this.control.reset());
  }

  onTagAdd(event) {
    const input = event.input;
    const value = event.value;

    if ((value || '').trim()) {
      this.pushTag({id: null, name: value.trim()});
    }
    if (input) {
      input.value = '';
    }
    this.control.setValue(null);
  }

  onTagRemove(value) {
    this.selectedTags.next(_.filter(this.selectedTags.value, v => v !== value));
  }

  onTagSelect(event: MatAutocompleteSelectedEvent): void {
    this.pushTag({id: event.option.value, name: event.option.viewValue});
    this.tagInput.nativeElement.value = '';
    this.control.setValue(null);
  }

  moveInputToSelectedTags(): VocabularyTagDTO[] {
    this.onTagAdd({value: this.control.value});
    return this.selectedTags.value;
  }

  ngOnDestroy(): void {
    this.unsubscribe.next(null);
  }

  private isTagNotSelected(tag: string): boolean {
    return !_.find(this.selectedTags.value, v => v.name.toLowerCase() === tag.toLowerCase());
  }

  private pushTag(tag: VocabularyTagDTO) {
    if (tag.name && this.isTagNotSelected(tag.name)) {
      this.selectedTags.next([...this.selectedTags.value, tag]);
    }
  }
}
