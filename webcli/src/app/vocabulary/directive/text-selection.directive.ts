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

  isValidSelection(selection: Selection): boolean {
    return (
        selection && selection.type === 'Range' &&
        (selection.anchorNode.parentElement.classList.contains('usage-example') || selection.isCollapsed));
  }

  isSelectionReset(selection: Selection): boolean {
    return selection && selection.type === 'None';
  }

  private listener = () => {
    const selection: Selection = document.getSelection();
    if (this.isValidSelection(selection)) {
      this.textSelection.emit(selection.toString());
    } else if (this.isSelectionReset(selection)) {
      this.textSelection.emit('');
    }
  };
}

