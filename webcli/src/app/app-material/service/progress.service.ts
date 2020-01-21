import {MatDialog, MatDialogRef} from '@angular/material';
import {HttpRequest, HttpResponse} from '@angular/common/http';
import {ProgressComponent} from '../dialog/progress.component';
import {Injectable} from '@angular/core';

@Injectable()
export class ProgressService {
  private readonly ignoreForUrls: string[] = [];
  private counter = 0;
  private dialogRef: MatDialogRef<ProgressComponent> = null;
  private cancellationHandler = null;

  constructor(private dialog: MatDialog) {
  }

  show(req: HttpRequest<any>) {
    if (this.ignoreForUrls.includes(req.url)) {
      return;
    }
    this.counter++;
    if (this.dialogRef == null && this.cancellationHandler == null) {
      this.cancellationHandler = setTimeout(() => {
        this.dialogRef = this.dialog.open(ProgressComponent, {minWidth: 200, minHeight: 250});
      }, 0);
    }
  }

  hide(res: HttpResponse<any>) {
    if (this.ignoreForUrls.includes(res.url)) {
      return;
    }
    this.counter--;
    if (this.counter === 0) {
      if (this.dialogRef != null) {
        this.dialogRef.close();
        this.dialogRef = null;
      } else if (this.cancellationHandler) {
        clearTimeout(this.cancellationHandler);
        this.cancellationHandler = null;
      }
    }
  }
}
