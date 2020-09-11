export interface Usage {
    readonly value: string;
    readonly examples: Example[];
}

export interface Example {
  readonly value: string;
  readonly hiddenWords: string[];
}

export interface MetaData {
    readonly usages: Usage[];
}
