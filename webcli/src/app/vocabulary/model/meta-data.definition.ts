export interface Translation {
  readonly translation: string;
  readonly examples: string[];
}

export interface MetaData {
  readonly translations: Translation[];
}


