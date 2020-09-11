export interface UsageEdit extends ValueGetter {
    /**
     * Remove usage example
     *
     * @param index example index to remove
     */
    removeExample(index: number);

    /**
     * Add usage example
     *
     * @param value Example value
     */
    addExample(value: string);

    /**
     * Get examples
     *
     * @return Examples associated with usage
     */
    getExamples(): ExampleEdit[];
}

export interface ValueGetter {
    getValue(): string;
}

export interface ExampleEdit extends ValueGetter {
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
     * Check if value is allowed to be hidden
     *
     * @param value Value to be check
     * @return true if value is allowed to be hidden, false otherwise
     */
    canBeHiddenWord(value: string): boolean;
}

