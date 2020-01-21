import {Component, EventEmitter, Inject, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {buildTranslationForm, TERM_TRANSLATION_FORM} from '../../form/term-translation.form';

@Component({
  selector: 'add-translation',
  templateUrl: './add-translation.component.html',
  styleUrls: ['./add-translation.component.scss'],
  providers: [
    {
      provide: TERM_TRANSLATION_FORM, useFactory: buildTranslationForm, deps: [FormBuilder]
    }
  ]
})
export class AddTranslationComponent implements OnInit {
  @Output() parseTranslation = new EventEmitter<string>();
  @Output() newInput = new EventEmitter<void>();

  constructor(@Inject(TERM_TRANSLATION_FORM) public form: FormGroup) {
  }

  ngOnInit() {
    this.form.reset();
  }

  parse() {
    this.newInput.emit();
    const translation: string = this.form.get('translation').value;
    translation
      .split(/[\n]/)
      .forEach(v =>
        this.parseTranslation.emit(v.replace(/^\s*\d\)/, '').trim()));
  }
}
