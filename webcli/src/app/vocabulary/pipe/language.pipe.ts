import {Pipe, PipeTransform} from '@angular/core';
import {LanguageDTO} from '../model/language.definition';

@Pipe({name: 'languageOption'})
export class LanguagePipe implements PipeTransform {
  transform(value: LanguageDTO = {code: '', name: '', nativeName: ''}): string {
    return value.name + ' (' + value.nativeName + ')';
  }
}
