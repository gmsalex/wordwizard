import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {VocabularySummaryDTO} from '../model/vocabulary-summary.definition';
import {VECreateDTO} from '../model/vocabulary-entry-create.definition';

@Injectable()
export class VocabularyService {
  constructor(private http: HttpClient) {
  }

  getVocabularySummary(): Observable<VocabularySummaryDTO> {
    return this.http.get('/vocabulary', {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }) as Observable<VocabularySummaryDTO>;
  }

  createVe(ve: VECreateDTO): Observable<VocabularySummaryDTO> {
    return this.http.post('/vocabulary/', ve) as Observable<VocabularySummaryDTO>;
  }
}
