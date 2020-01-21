export interface Translation {
  readonly value: string;
  readonly examples: Example[];
}

export interface Example {
  readonly value: string;
  readonly hiddenWords: string[];
}

export interface MetaData {
  readonly translations: Translation[];
}

export interface TranslationEdit extends Translation {
  /**
   * Remove translation example
   *
   * @param index example index to remove
   */
  removeExample(index: number);

  /**
   * Add translation example
   *
   * @param value Example value
   */
  addExample(value: string);

  /**
   * Get examples
   *
   * @return Examples associated with translation
   */
  getExamples(): ExampleEdit[];
}

export interface ExampleEdit extends Example {
  /**
   * Add new word marked as hidden
   *
   * @param value Value that will be hidden
   */
  addHiddenWord(value: string);

  /**
   * Remove hidden word
   *
   * @param value Value that will be removed from hidden
   */
  removeHiddenWord(value: string);

  /**
   * Check whether value is allowed to be hidden
   *
   * @param value Value to be check
   * @return true if value is allowed to be hidden, false otherwise
   */
  canBeHiddenWord(value: string): boolean;
}


