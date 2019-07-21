import {Component, EventEmitter, Inject, OnInit, Output} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {TERM_TRANSLATION_FORM} from "../../form/term-translation.form";

@Component({
  selector: 'add-translation',
  templateUrl: './add-translation.component.html',
  styleUrls: ['./add-translation.component.scss']
})
export class AddTranslationComponent implements OnInit {
  @Output() parseTranslation = new EventEmitter<string>();

  constructor(@Inject(TERM_TRANSLATION_FORM) private form: FormGroup) {
  }

  ngOnInit() {
  }

  parse() {
    let translation: string = this.form.get('translation').value;
    translation
      .split(/[\n]/)
      .forEach(v =>
        this.parseTranslation.emit(v.replace(/^\s*\d\)/, '').trim()));
  }
}
