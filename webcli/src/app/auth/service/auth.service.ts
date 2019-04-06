import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {switchMap} from "rxjs/operators";
import {UserAuthRequest, UserAuthSuccessResponse} from "../store/definition/user-auth.definition";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {
  }

  login(credentials: UserAuthRequest): Observable<UserAuthSuccessResponse> {
    // make GET request to instantiate XSRF token
    return this.http.get('/login').pipe(
      switchMap(() => <Observable<UserAuthSuccessResponse>>this.http.post('/login', credentials))
    );
  }
}
