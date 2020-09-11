import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {UsageEdit} from '../../model/usage/usage.definition';
import {createUsage} from '../../model/usage/usage.implementation';

@Injectable()
export class UsageDatabase {
  private usage: BehaviorSubject<UsageEdit[]> = new BehaviorSubject<UsageEdit[]>([]);

  get data(): Observable<UsageEdit[]> {
    return this.usage;
  }

  addUsage(value: string) {
    const values = [...this.usage.value];
    values.push(createUsage(value));
    this.usage.next(values);
  }

  removeUsage(index: number) {
    const values = [...this.usage.value];
    values.splice(index, 1);
    this.usage.next(values);
  }

  mergeUsageToExamples(index: number) {
    if (index > 0 && index < this.usage.value.length) {
      const dst: UsageEdit = this.usage.value[index - 1];
      const src: UsageEdit = this.usage.value[index];
      const example = src.getValue();
      dst.addExample(example);
      this.removeUsage(index);
    }
  }

  close() {
    this.usage.complete();
  }
}
