import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {ProgressService} from '../app-material/service/progress.service';

@Injectable()
export class ProgressInterceptor {

  constructor(private progressService: ProgressService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.progressService.show(req);
    return next.handle(req).pipe(tap((e: HttpEvent<any>) => {
      if (e instanceof HttpResponse) {
        this.progressService.hide(e);
      }
    }, (e: any) => {
      this.progressService.hide(e);
    }));
  }
}
