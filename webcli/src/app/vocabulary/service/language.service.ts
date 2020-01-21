import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {LanguageDTO} from '../model/language.definition';
import {catchError, take} from 'rxjs/operators';
import * as _ from 'lodash';

@Injectable()
export class LanguageService {
  private languageSubject: BehaviorSubject<LanguageDTO[]> = new BehaviorSubject<LanguageDTO[]>([]);

  constructor(private http: HttpClient) {
  }

  getLanguage(code: string) {
    return _.find(this.languageSubject.value, {code});
  }

  listLanguages(): Observable<LanguageDTO[]> {
    if (this.languageSubject.value.length === 0) {
      this.refresh();
    }
    return this.languageSubject;
  }

  private refresh() {
    this.http.get('/vocabulary/languages', {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    })
      .pipe(
        catchError(() => of([])),
        take(1)
      ).subscribe((languages: LanguageDTO[]) => this.languageSubject.next(languages));
  }
}
