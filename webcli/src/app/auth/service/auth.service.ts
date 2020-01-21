import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {switchMap} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {UserAuthRequest, UserAuthSuccessResponse} from '../model/definition/user-auth.definition';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {
  }

  login(credentials: UserAuthRequest): Observable<UserAuthSuccessResponse> {
    // make GET request to instantiate XSRF token
    return this.http.get('/login').pipe(
      switchMap(() => this.http.post('/login', credentials) as Observable<UserAuthSuccessResponse>)
    );
  }
}
