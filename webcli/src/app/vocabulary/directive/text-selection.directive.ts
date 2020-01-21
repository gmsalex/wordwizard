import {Directive, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';

@Directive({
  selector: '[textselection]'
})
export class TextSelectionDirective implements OnInit, OnDestroy {
  @Output()
  textSelection: EventEmitter<string> = new EventEmitter();

  constructor() {
  }

  ngOnDestroy(): void {
    document.removeEventListener('selectionchange', this.listener);
  }

  ngOnInit(): void {
    document.addEventListener('selectionchange', this.listener);
  }

  validSelection(selection: Selection): boolean {
    return (
      selection && selection.type === 'Range' &&
      (selection.anchorNode.parentElement.classList.contains('translation-example') || selection.isCollapsed));
  }

  private listener = () => {
    const selection: Selection = document.getSelection();
    if (this.validSelection(selection)) {
      this.textSelection.emit(selection.toString());
    }
  };
}

