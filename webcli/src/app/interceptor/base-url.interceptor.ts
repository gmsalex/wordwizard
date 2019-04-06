import {Inject, Injectable, InjectionToken} from "@angular/core";
import {HttpEvent, HttpHandler, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

export const BASE_URL = new InjectionToken<string>('Base URL');

@Injectable()
export class BaseUrlInterceptor {

  constructor(@Inject(BASE_URL) private baseURL: string) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    req = req.clone({url: this.baseURL + req.url});
    return next.handle(req);
  }
}
