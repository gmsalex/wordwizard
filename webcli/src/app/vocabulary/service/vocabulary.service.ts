import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RepetitionDTO} from '../model/repetition.definition';
import {VECreationWrapper} from '../model/vocabulary-entry.definition';

@Injectable()
export class VocabularyService {
  constructor(private http: HttpClient) {
  }

  listVs(): Observable<VocabularySelectionDTO[]> {
    return this.http.get('/vocabulary', {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }) as Observable<VocabularySelectionDTO[]>;
  }

  createVs(vs: VSCreationDTO): Observable<VocabularySelectionDTO> {
    return this.http.post('/vocabulary', vs) as Observable<VocabularySelectionDTO>;
  }

  createVe(veWrapper: VECreationWrapper): Observable<RepetitionDTO> {
    return this.http.post('/vocabulary/' + veWrapper.vsId + '/entry', veWrapper.ve) as Observable<RepetitionDTO>;
  }

  listRepetition(vsId: number) {
    return this.http.get('/vocabulary/' + vsId + '/repetition', {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    }) as Observable<RepetitionDTO[]>;
  }
}
