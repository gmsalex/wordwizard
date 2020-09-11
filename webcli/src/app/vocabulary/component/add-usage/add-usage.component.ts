import {Component, EventEmitter, Inject, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {buildUsageForm, TERM_USAGE_FORM} from '../../form/term-translation.form';
import {Observable, Subscription} from 'rxjs';

@Component({
  selector: 'add-usage',
  templateUrl: './add-usage.component.html',
  styleUrls: ['./add-usage.component.scss'],
  providers: [
    {
      provide: TERM_USAGE_FORM, useFactory: buildUsageForm, deps: [FormBuilder]
    }
  ]
})
export class AddUsageComponent implements OnInit, OnDestroy {
  @Output() parseUsage = new EventEmitter<string>();
  @Output() newInput = new EventEmitter<void>();
  @Input() resetNotification: Observable<void>;
  subscription: Subscription;

  constructor(@Inject(TERM_USAGE_FORM) public form: FormGroup) {
  }

  ngOnInit() {
    this.form.reset();
    this.subscription = this.resetNotification.subscribe(v => this.form.reset());
  }

  parse() {
    this.newInput.emit();
    const usages: string = this.form.get('usage').value;
    usages
        .split(/[\n]/)
        .forEach(v =>
            this.parseUsage.emit(v.replace(/^\s*\d\)/, '').trim()));
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
