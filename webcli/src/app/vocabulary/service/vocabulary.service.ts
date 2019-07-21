import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {RepetitionDTO} from "../model/repetition.definition";

@Injectable()
export class VocabularyService {
  constructor(private http: HttpClient) {
  }

  listVs(): Observable<VocabularySelectionDTO[]> {
    return <Observable<VocabularySelectionDTO[]>>this.http.get('/vocabulary', {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    });
  }

  createVs(vs: VSCreationDTO): Observable<VocabularySelectionDTO> {
    return <Observable<VocabularySelectionDTO>>this.http.post('/vocabulary', vs);
  }

  listRepetition(vsId: number) {
    return <Observable<RepetitionDTO[]>>this.http.get('/vocabulary/' + vsId + '/repetition', {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    });
  }
}
