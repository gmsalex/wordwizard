import {Example, Usage} from '../meta-data.definition';
import {ExampleEdit, UsageEdit} from './usage.definition';

class DefaultExample implements Example, ExampleEdit {
  readonly value: string;
  readonly hiddenWords: string[] = [];

  constructor(value: string) {
    this.value = value;
  }

  addHiddenWord(value: string) {
    if (this.canBeHiddenWord(value)) {
      this.hiddenWords.push(value.trim());
    }
  }

  removeHiddenWord(value: string) {
    for (let i = 0; i < this.hiddenWords.length; i++) {
      if (this.hiddenWords[i] === value) {
        this.hiddenWords.splice(i, 1);
      }
    }
  }

  canBeHiddenWord(value: string) {
    value = (value || '').trim();
    return (value && this.value.indexOf(value, 0) > -1 && this.hiddenWords.filter(v => v === value).length === 0);
  }

  getValue(): string {
    return this.value;
  }
}

class DefaultUsage implements UsageEdit, Usage {
  readonly value: string;
  readonly examples: DefaultExample[] = [];

  constructor(value: string) {
    this.value = value;
  }

  addExample(value: string) {
    this.examples.push(new DefaultExample(value));
  }

  getExamples(): ExampleEdit[] {
    return this.examples as ExampleEdit[];
  }

  getValue(): string {
    return this.value;
  }

  removeExample(index: number) {
    this.examples.splice(index, 1);
  }
}

export function createUsage(value: string): UsageEdit {
  return new DefaultUsage(value);
}

